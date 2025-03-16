package com.yaikostudio.kparsetron.parsers.xvideos

import com.yaikostudio.kparsetron.entities.ParsedSiteData
import com.yaikostudio.kparsetron.entities.ParsedVideoDetail
import com.yaikostudio.kparsetron.entities.media.Media
import com.yaikostudio.kparsetron.entities.media.MediaFileAlternative
import com.yaikostudio.kparsetron.entities.media.MediaThumbnail
import com.yaikostudio.kparsetron.entities.media.StreamingType
import com.yaikostudio.kparsetron.entities.media.VideoContainer
import com.yaikostudio.kparsetron.entities.media.VideoDetail
import com.yaikostudio.kparsetron.entities.media.VideoPart
import com.yaikostudio.kparsetron.entities.media.VideoRelated
import com.yaikostudio.kparsetron.entities.network.ResourceUrl
import com.yaikostudio.kparsetron.entities.users.Account
import com.yaikostudio.kparsetron.network.Downloader
import com.yaikostudio.kparsetron.parsers.Parser
import com.yaikostudio.kparsetron.parsers.extensions.findJson
import com.yaikostudio.kparsetron.parsers.extensions.parseLongFromNumberWithUnit
import com.yaikostudio.kparsetron.parsers.extensions.toUrl
import io.ktor.http.Url
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class XvideosVideoParser(
    private val downloader: Downloader,
) : Parser() {
    private val REGEX_VIDEO_URL_HLS by lazy { "html5player.setVideoHLS\\('(.*?)'\\);".toRegex() }
    private val REGEX_VIDEO_URL_HIGH by lazy { "html5player.setVideoUrlHigh\\('(.*?)'\\);".toRegex() }
    private val REGEX_VIDEO_URL_LOW by lazy { "html5player.setVideoUrlLow\\('(.*?)'\\);".toRegex() }
    private val REGEX_VIDEO_RELATED by lazy { "var video_related=(\\[.*?]);".toRegex() }

    override fun supportedHosts() = listOf(
        "www.xvideos.com",
        "xvideos.com",
    )

    override suspend fun supports(url: Url): Boolean {
        return url.encodedPath.startsWith("/video")
    }

    override suspend fun parse(url: Url): Result<ParsedSiteData> {
        val (html, doc) = downloader.getAndParse(url, null)

        val videoScript = doc.select("#video-player-bg > script:nth-child(6)").html()

        val videoAlternatives = mapOf(
            REGEX_VIDEO_URL_HLS to Media.Video(
                streamingType = StreamingType.HLS,
                caption = "Auto",
            ),
            REGEX_VIDEO_URL_HIGH to Media.Video(
                streamingType = StreamingType.DIRECT,
                container = VideoContainer.MP4,
                caption = "HQ",
            ),
            REGEX_VIDEO_URL_LOW to Media.Video(
                streamingType = StreamingType.DIRECT,
                container = VideoContainer.MP4,
                caption = "Low",
            ),
        ).mapNotNull { (regex, media) ->
            regex.find(videoScript)?.groupValues?.get(1)?.let {
                MediaFileAlternative(
                    media = media,
                    url = ResourceUrl.Unprotected(it.toUrl()),
                )
            }
        }

        if (videoAlternatives.isEmpty()) {
            return Result.failure(IllegalArgumentException("Unable to parse video URL"))
        }

        val related = json.findJson<List<XvideosRelatedVideo>>(REGEX_VIDEO_RELATED, html) ?: emptyList()

        val account = doc.select("a.uploader-tag")
        val parsed = ParsedVideoDetail(
            data = VideoDetail(
                title = doc.select("meta[property=\"og:title\"]").attr("content"),
                owner = Account(
                    id = account.select(".user-subscribe").attr("data-user-id"),
                    name =  account.select(".name").text().trim(),
                    username = account.select(".user-subscribe").attr("data-user-profile"),
                    isVerified = false,
                    profilePicUrl = null,
                    followers = account.select(".count").text().parseLongFromNumberWithUnit(),
                ),
                duration = doc.select("meta[property=\"og:duration\"]").attr("content").toLongOrNull()?.toDuration(DurationUnit.SECONDS),
                viewCount = doc.select("#nb-views-number").text().replace(",", "").toLongOrNull(),
                thumbnails = listOf(
                    MediaThumbnail(
                        type = MediaThumbnail.Type.IMAGE,
                        url = doc.select("meta[property=\"og:image\"]").attr("content"),
                        size = null,
                    ),
                ),
                parts = listOf(
                    VideoPart(
                        title = null,
                        videoAlternatives = videoAlternatives,
                        audioAlternatives = emptyList(),
                    ),
                ),
                relatedMedia = related.map {
                    VideoRelated(
                        title = it.title,
                        url = "https://www.xvideos.com/${it.url.removePrefix("/")}".toUrl(),
                        thumbnails = it.toMediaThumbnails(),
                    )
                },
                upNext = emptyList(),
                comments = emptyList(),
            ),
        )

        return Result.success(parsed)
    }
}
