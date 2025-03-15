package com.yaikostudio.kparsetron.parsers.instagram

import com.yaikostudio.kparsetron.entities.ParsedSiteData
import com.yaikostudio.kparsetron.entities.ParsedVideoDetail
import com.yaikostudio.kparsetron.entities.media.Media
import com.yaikostudio.kparsetron.entities.media.MediaFileAlternative
import com.yaikostudio.kparsetron.entities.media.StreamingType
import com.yaikostudio.kparsetron.entities.media.VideoContainer
import com.yaikostudio.kparsetron.entities.media.VideoDetail
import com.yaikostudio.kparsetron.entities.media.VideoPart
import com.yaikostudio.kparsetron.network.Downloader
import com.yaikostudio.kparsetron.network.NetworkHelperKtor.Companion.getCookies
import com.yaikostudio.kparsetron.parsers.extensions.hasAnySegment
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.parseUrl
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class InstagramReelParser(
    private val downloader: Downloader,
) : InstagramBaseParser() {
    private val VALID_URL by lazy {
        """(?<url>https?://(?:www\.)?instagram\.com(?:/(?!share/)[^/?#]+)?/(?:p|tv|reels?(?!/audio/))/(?<id>[^/?#&]+))""".toRegex()
    }

    override suspend fun supports(url: Url): Boolean {
        if (url.host !in listOf("www.instagram.com", "instagram.com")) {
            return false
        }

        if (url.hasAnySegment(0..1, "p", "tv", "reel")) {
            return true
        }

        return false
    }

    override suspend fun parse(url: Url): ParsedSiteData? {
        val matches = VALID_URL.find(url.toString()) ?: return null
        val videoId = matches.groups["id"]?.value ?: return null

        val rulingContentUrl = parseUrl("$API_BASE_URL/web/get_ruling_for_content/?content_type=MEDIA&target_id=" + idToPk(videoId)) ?: return null

        val response = downloader.get(
            url = rulingContentUrl,
            extraHeaders = apiHeaders,
        )

        val csrfToken = response.getCookies().find {
            it.name == "csrftoken"
        }?.value

        val variables = GeneralInfoRequest(
            shortcode = videoId,
        )
        val generalInfoUrl = URLBuilder("https://www.instagram.com/graphql/query/")
            .apply {
                parameters.append("doc_id", "8845758582119845")
                parameters.append("variables", json.encodeToString(variables))
            }
            .build()

        val generalInfoResponse = downloader.get(
            url = generalInfoUrl,
            referrer = url,
            extraHeaders = apiHeaders + mapOf(
                "X-CSRFToken" to (csrfToken ?: ""),
                "X-Requested-With" to "XMLHttpRequest",
            ),
        )
        val generalInfo = json.decodeFromString<GeneralInfoResponse>(generalInfoResponse.bodyAsText())

        return with(generalInfo.data.xdtShortcodeMedia) {
            ParsedVideoDetail(
                data = VideoDetail(
                    title = edgeMediaToCaption.edges.first().node.text,
                    duration = videoDuration?.toDuration(DurationUnit.SECONDS),
                    viewCount = videoViewCount,
                    thumbnails = displayResources.map { it.toMediaThumbnail() },
                    parts = listOf(
                        VideoPart(
                            title = null,
                            videoAlternatives = listOf(
                                MediaFileAlternative(
                                    media = Media.Video(
                                        streamingType = StreamingType.DIRECT,
                                        container = VideoContainer.MP4,
                                        videoCodec = null,
                                    ),
                                    url = parseUrl(videoUrl!!) ?: return null,
                                ),
                            ),
                            audioAlternatives = emptyList(),
                        )
                    ),
                    relatedMedia = emptyList(),
                    upNext = emptyList(),
                ),
            )
        }
    }
}
