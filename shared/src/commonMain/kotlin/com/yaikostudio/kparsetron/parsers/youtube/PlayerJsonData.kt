package com.yaikostudio.kparsetron.parsers.youtube

import kotlinx.serialization.Serializable

@Serializable
data class PlayerJsonData(
    val videoDetails: VideoDetails,
    val streamingData: StreamingData,
    val microformat: MicroFormat,
) {
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
    data class Thumbnail(
        val thumbnails: List<ThumbnailData>,
    ) {
        @Serializable
        data class ThumbnailData(
            val url: String,
            val width: Int,
            val height: Int,
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
        val thumbnail: Thumbnail,
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
            val thumbnail: Thumbnail,
        )
    }
}
