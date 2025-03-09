package com.yaikostudio.kparsetron.entities.media

import kotlin.time.Duration

data class VideoDetail(
    val title: String,
    val duration: Duration?,
    val viewCount: Long?,
    val thumbnails: List<MediaThumbnail>,
    val parts: List<VideoPart>,
)
