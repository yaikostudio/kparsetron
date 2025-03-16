package com.yaikostudio.kparsetron.parsers.xvideos

import com.yaikostudio.kparsetron.entities.media.MediaThumbnail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class XvideosRelatedVideo(
    @SerialName("tf") val title: String,
    @SerialName("u") val url: String,
    @SerialName("i") val thumbnail: String,
    @SerialName("il") val thumbnailLarge: String,
    @SerialName("if") val thumbnailFull: String,
    @SerialName("ip") val thumbnailPoster: String,
) {
    fun toMediaThumbnails(): List<MediaThumbnail> {
        return setOf(
            thumbnail,
            thumbnailLarge,
            thumbnailFull,
            thumbnailPoster,
        ).map {
            MediaThumbnail(
                type = MediaThumbnail.Type.IMAGE,
                url = it,
                size = null,
            )
        }
    }
}
