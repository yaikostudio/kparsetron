package com.yaikostudio.kparsetron.entities.media

import com.yaikostudio.kparsetron.entities.network.ResourceUrl

data class MediaFileAlternative(
    val media: Media,
    val url: ResourceUrl,
)
