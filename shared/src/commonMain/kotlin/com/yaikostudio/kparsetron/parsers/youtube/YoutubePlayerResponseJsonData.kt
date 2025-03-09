package com.yaikostudio.kparsetron.parsers.youtube

import kotlinx.serialization.Serializable

@Serializable
data class YoutubePlayerResponseJsonData(
    val videoDetails: VideoDetails,
    val streamingData: StreamingData,
    val microformat: MicroFormat,
) {
    val allThumbnails: List<YoutubeThumbnail.ThumbnailData>
        get() = videoDetails.thumbnail.thumbnails + microformat.playerMicroformatRenderer.thumbnail.thumbnails

    @Serializable
    data class StreamingData(
        val adaptiveFormats: List<AdaptiveFormat>,
        val formats: List<Format>,
    ) {
        @Serializable
        data class Format(
            val itag: String,
            val signatureCipher: String,
        )

        @Serializable
        data class AdaptiveFormat(
            val itag: String,
            val signatureCipher: String,
        )
    }

    @Serializable
    data class VideoDetails(
        val title: String,
        val videoId: String,
        val lengthSeconds: String,
        val channelId: String,
        val author: String,
        val shortDescription: String,
        val viewCount: String,
        val isLiveContent: Boolean,
        val thumbnail: YoutubeThumbnail,
    )

    @Serializable
    data class MicroFormat(
        val playerMicroformatRenderer: PlayerMicroFormatRenderer,
    ) {
        @Serializable
        data class PlayerMicroFormatRenderer(
            val ownerProfileUrl: String,
            val ownerChannelName: String,
            val uploadDate: String,
            val thumbnail: YoutubeThumbnail,
        )
    }
}
