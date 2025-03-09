package com.yaikostudio.kparsetron.entities.media

data class VideoSummary(
    val title: String,
    val thumbnailURL: String?,
    val detailURL: String,
    val rating: Float?,
    val year: Int?,
    val referrer: String,
)
