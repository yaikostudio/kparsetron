package com.yaikostudio.kparsetron.entities.media

import io.ktor.http.Url

data class MediaFileAlternative(
    val media: Media,
    val url: Url,
)
