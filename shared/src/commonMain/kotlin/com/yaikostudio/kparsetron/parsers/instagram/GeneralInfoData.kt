package com.yaikostudio.kparsetron.parsers.instagram

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GeneralInfoData(
    @SerialName("shortcode") val shortcode: String,
    @SerialName("child_comment_count") val childCommentCount: Int,
    @SerialName("fetch_comment_count") val fetchCommentCount: Int,
    @SerialName("parent_comment_count") val parentCommentCount: Int,
    @SerialName("has_threaded_comments") val hasThreadedComments: Boolean,
)
