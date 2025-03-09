package com.yaikostudio.kparsetron.parsers.youtube

import com.yaikostudio.kparsetron.entities.ParsedSiteData
import com.yaikostudio.kparsetron.entities.ParsedVideoDetail
import com.yaikostudio.kparsetron.entities.media.Media
import com.yaikostudio.kparsetron.entities.media.MediaFileAlternative
import com.yaikostudio.kparsetron.entities.media.MediaThumbnail
import com.yaikostudio.kparsetron.entities.media.Size
import com.yaikostudio.kparsetron.entities.media.VideoDetail
import com.yaikostudio.kparsetron.entities.media.VideoPart
import com.yaikostudio.kparsetron.network.Downloader
import com.yaikostudio.kparsetron.parsers.Parser
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

    override suspend fun parse(url: Url): ParsedSiteData? {
        val doc = downloader.getAndParse(url, null)

        val bodyString = doc.body().toString()
        val match = initialPlayerResponseRegex.find(bodyString) ?: return null
        val jsonString = match.groupValues.getOrNull(1) ?: return null

        val data: PlayerJsonData = json.decodeFromString(jsonString)
        println("MATCH: $data")

        val videoAlternatives = mutableListOf<MediaFileAlternative>()
        val audioAlternatives = mutableListOf<MediaFileAlternative>()
        data.streamingData.adaptiveFormats.forEach { format ->
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
                title = data.videoDetails.title,
                duration = data.videoDetails.lengthSeconds.toLongOrNull()?.toDuration(DurationUnit.SECONDS),
                viewCount = data.videoDetails.viewCount.toLongOrNull(),
                thumbnails = (data.videoDetails.thumbnail.thumbnails + data.microformat.playerMicroformatRenderer.thumbnail.thumbnails).map {
                    MediaThumbnail(
                        type = MediaThumbnail.Type.IMAGE,
                        url = it.url,
                        size = Size(
                            width = it.width,
                            height = it.height,
                        ),
                    )
                },
                parts = listOf(
                    VideoPart(
                        title = null,
                        videoAlternatives = videoAlternatives,
                        audioAlternatives = audioAlternatives,
                    ),
                ),
            ),
        )
    }
}
