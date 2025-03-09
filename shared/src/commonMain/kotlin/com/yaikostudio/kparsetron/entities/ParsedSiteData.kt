package com.yaikostudio.kparsetron.entities

import com.yaikostudio.kparsetron.entities.media.VideoDetail

sealed class ParsedSiteData

data class ParsedVideoDetail(
    val data: VideoDetail,
) : ParsedSiteData()
