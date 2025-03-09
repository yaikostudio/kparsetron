package com.yaikostudio.kparsetron.parsers.youtube

import com.yaikostudio.kparsetron.entities.media.MediaThumbnail
import com.yaikostudio.kparsetron.entities.media.Size
import kotlinx.serialization.Serializable

@Serializable
data class YoutubeThumbnail(
    val thumbnails: List<ThumbnailData>,
) {
    @Serializable
    data class ThumbnailData(
        val url: String,
        val width: Int,
        val height: Int,
    ) {
        fun toMediaThumbnail() = MediaThumbnail(
            type = MediaThumbnail.Type.IMAGE,
            url = url,
            size = Size(
                width = width,
                height = height,
            ),
        )
    }
}
