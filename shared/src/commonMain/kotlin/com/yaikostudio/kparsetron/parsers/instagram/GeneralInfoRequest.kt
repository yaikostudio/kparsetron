package com.yaikostudio.kparsetron.parsers.instagram

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GeneralInfoRequest(
    @SerialName("shortcode") val shortcode: String,
    @SerialName("child_comment_count") val childCommentCount: Int = 3,
    @SerialName("fetch_comment_count") val fetchCommentCount: Int = 40,
    @SerialName("parent_comment_count") val parentCommentCount: Int = 24,
    @SerialName("has_threaded_comments") val hasThreadedComments: Boolean = true,
)
