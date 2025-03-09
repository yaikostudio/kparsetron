package com.yaikostudio.kparsetron.parsers.youtube

import com.yaikostudio.kparsetron.entities.ParsedSiteData
import com.yaikostudio.kparsetron.entities.ParsedVideoDetail
import com.yaikostudio.kparsetron.entities.media.Media
import com.yaikostudio.kparsetron.entities.media.MediaFileAlternative
import com.yaikostudio.kparsetron.entities.media.MediaThumbnail
import com.yaikostudio.kparsetron.entities.media.Size
import com.yaikostudio.kparsetron.entities.media.VideoDetail
import com.yaikostudio.kparsetron.entities.media.VideoPart
import com.yaikostudio.kparsetron.entities.media.VideoRelated
import com.yaikostudio.kparsetron.network.Downloader
import com.yaikostudio.kparsetron.parsers.Parser
import com.yaikostudio.kparsetron.parsers.utils.findJson
import io.ktor.http.Url
import kotlinx.serialization.json.Json
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class YoutubeDetailParser(
    private val downloader: Downloader,
) : Parser {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val initialPlayerResponseRegex: Regex by lazy { "ytInitialPlayerResponse\\s*=\\s*(\\{.+?\\})\\s*;".toRegex() }
    private val initialDataRegex: Regex by lazy { "ytInitialData\\s*=\\s*(\\{.+?\\})\\s*;".toRegex() }

    override suspend fun parse(url: Url): ParsedSiteData? {
        val doc = downloader.getAndParse(url, null)

        val bodyString = doc.body().toString()

        val playerResponseData = json.findJson<YoutubePlayerResponseJsonData>(initialPlayerResponseRegex, bodyString) ?: return null
        val initialJsonData = json.findJson<YoutubeInitialJsonData>(initialDataRegex, bodyString) ?: return null

        val videoAlternatives = mutableListOf<MediaFileAlternative>()
        val audioAlternatives = mutableListOf<MediaFileAlternative>()
        playerResponseData.streamingData.adaptiveFormats.forEach { format ->
            val media = ITAG_MAP[format.itag] ?: return null

            val file = MediaFileAlternative(
                media = media,
            )
            when(media) {
                is Media.Video -> videoAlternatives.add(file)
                is Media.Audio -> audioAlternatives.add(file)
            }
        }

        return ParsedVideoDetail(
            data = VideoDetail(
                title = playerResponseData.videoDetails.title,
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
                upNext = initialJsonData.playerOverlays.playerOverlayRenderer.endScreen.watchNextEndScreenRenderer.results.map {
                    VideoRelated(
                        title = it.endScreenVideoRenderer.title.simpleText,
                        url = Url("https://www.youtube.com/watch?v=${it.endScreenVideoRenderer.videoId}"),
                        thumbnails = it.endScreenVideoRenderer.thumbnail.thumbnails.map {
                            it.toMediaThumbnail()
                        },
                    )
                }
            ),
        )
    }
}
