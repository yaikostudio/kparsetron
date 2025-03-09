package com.yaikostudio.kparsetron.entities.media

data class MediaThumbnail  (
    val type: Type,
    val url: String,
    val size: Size?,
) {
    enum class Type {
        IMAGE,
        VIDEO,
    }
}
