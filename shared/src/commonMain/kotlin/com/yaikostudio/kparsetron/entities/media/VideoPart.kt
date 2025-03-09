package com.yaikostudio.kparsetron.entities.media

data class VideoPart(
    val title: String?,
    val videoAlternatives: List<MediaFileAlternative>,
    val audioAlternatives: List<MediaFileAlternative>,
)
