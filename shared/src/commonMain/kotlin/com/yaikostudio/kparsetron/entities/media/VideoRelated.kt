package com.yaikostudio.kparsetron.entities.media

import io.ktor.http.Url

data class VideoRelated(
    val title: String,
    val url: Url,
    val thumbnails: List<MediaThumbnail>,
)
