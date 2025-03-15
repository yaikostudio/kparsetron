package com.yaikostudio.kparsetron.entities.media

import com.yaikostudio.kparsetron.entities.interactions.Comment
import com.yaikostudio.kparsetron.entities.users.Account
import kotlin.time.Duration

data class VideoDetail(
    val title: String,
    val owner: Account,
    val duration: Duration?,
    val viewCount: Long?,
    val thumbnails: List<MediaThumbnail>,
    val parts: List<VideoPart>,
    val relatedMedia: List<VideoRelated>,
    val upNext: List<VideoRelated>,
    val comments: List<Comment>,
)
