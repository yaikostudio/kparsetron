package com.yaikostudio.kparsetron.parsers.youtube

import com.yaikostudio.kparsetron.entities.ParsedSiteData
import com.yaikostudio.kparsetron.entities.ParsedVideoDetail
import com.yaikostudio.kparsetron.entities.media.Media
import com.yaikostudio.kparsetron.entities.media.MediaFileAlternative
import com.yaikostudio.kparsetron.entities.media.VideoDetail
import com.yaikostudio.kparsetron.entities.media.VideoPart
import com.yaikostudio.kparsetron.entities.media.VideoRelated
import com.yaikostudio.kparsetron.entities.network.ResourceUrl
import com.yaikostudio.kparsetron.entities.users.Account
import com.yaikostudio.kparsetron.network.Downloader
import com.yaikostudio.kparsetron.parsers.Parser
import com.yaikostudio.kparsetron.parsers.extensions.findJson
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.parseQueryString
import io.ktor.http.parseUrl
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class YoutubeDetailParser(
    private val downloader: Downloader,
) : Parser() {

    private val initialPlayerResponseRegex: Regex by lazy { "ytInitialPlayerResponse\\s*=\\s*(\\{.+?\\})\\s*;".toRegex() }
    private val initialDataRegex: Regex by lazy { "ytInitialData\\s*=\\s*(\\{.+?\\})\\s*;".toRegex() }

    override fun supportedHosts() = listOf(
        "www.youtube.com",
        "youtube.com",
        "youtu.be",
    )

    override suspend fun supports(url: Url): Boolean {
        return when (url.host) {
            "www.youtube.com" -> url.encodedPath.startsWith("/watch")
            "youtube.com" -> url.encodedPath.startsWith("/watch")
            "youtu.be" -> true
            else -> false
        }
    }

    override suspend fun parse(url: Url): Result<ParsedSiteData> {
        val bodyString = downloader.get(url, null).bodyAsText()

        val playerResponseData = json.findJson<YoutubePlayerResponseJsonData>(initialPlayerResponseRegex, bodyString)
        playerResponseData ?: return Result.failure(IllegalArgumentException("Unable to parse player response data"))
        val initialJsonData = json.findJson<YoutubeInitialJsonData>(initialDataRegex, bodyString)
        initialJsonData ?: return Result.failure(IllegalArgumentException("Unable to parse initial data"))

        val videoAlternatives = mutableListOf<MediaFileAlternative>()
        val audioAlternatives = mutableListOf<MediaFileAlternative>()
        playerResponseData.streamingData.adaptiveFormats.forEach { format ->
            val media = ITAG_MAP[format.itag] ?: return Result.failure(IllegalArgumentException("Unknown ITAG: ${format.itag}"))

            val parsedUrlVideoParameters = parseQueryString(format.signatureCipher)
            val fmtVideoUrl = parseUrl(parsedUrlVideoParameters["url"]!!) ?: return Result.failure(IllegalArgumentException("Unable to parse video URL"))
            val fmtVideoSignature = parsedUrlVideoParameters["s"] ?: return Result.failure(IllegalArgumentException("Unable to parse video signature"))
            val fmtVideoSp = parsedUrlVideoParameters["sp"] ?: return Result.failure(IllegalArgumentException("Unable to parse video sp"))

            val file = MediaFileAlternative(
                media = media,
                url = ResourceUrl.Unsupported,
            )
            when (media) {
                is Media.Video -> videoAlternatives.add(file)
                is Media.Audio -> audioAlternatives.add(file)
            }
        }

        val parsed = ParsedVideoDetail(
            data = VideoDetail(
                title = playerResponseData.videoDetails.title,
                owner = Account(
                    id = playerResponseData.videoDetails.channelId,
                    name = playerResponseData.videoDetails.author,
                    username = playerResponseData.videoDetails.channelId,
                    isVerified = false,
                    profilePicUrl = null,
                    followers = null,
                ),
                duration = playerResponseData.videoDetails.lengthSeconds.toLongOrNull()?.toDuration(DurationUnit.SECONDS),
                viewCount = playerResponseData.videoDetails.viewCount.toLongOrNull(),
                thumbnails = playerResponseData.allThumbnails.map {
                    it.toMediaThumbnail()
                },
                parts = listOf(
                    VideoPart(
                        title = null,
                        videoAlternatives = videoAlternatives,
                        audioAlternatives = audioAlternatives,
                    ),
                ),
                relatedMedia = initialJsonData.contents.twoColumnWatchNextResults.secondaryResults.secondaryResults.results.mapNotNull {
                    it.compactVideoRenderer
                }.map { v ->
                    VideoRelated(
                        title = v.title.simpleText,
                        url = Url("https://www.youtube.com/watch?v=${v.videoId}"),
                        thumbnails = v.thumbnail.thumbnails.map {
                            it.toMediaThumbnail()
                        },
                    )
                },
                upNext = initialJsonData.playerOverlays.playerOverlayRenderer.endScreen.watchNextEndScreenRenderer.results.mapNotNull {
                    it.endScreenVideoRenderer
                }.map {
                    VideoRelated(
                        title = it.title.simpleText,
                        url = Url("https://www.youtube.com/watch?v=${it.videoId}"),
                        thumbnails = it.thumbnail.thumbnails.map {
                            it.toMediaThumbnail()
                        },
                    )
                },
                comments = emptyList(),
            ),
        )

        return Result.success(parsed)
    }
}
