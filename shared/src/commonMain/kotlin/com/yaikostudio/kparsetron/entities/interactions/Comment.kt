package com.yaikostudio.kparsetron.entities.interactions

import com.yaikostudio.kparsetron.entities.users.Account
import kotlinx.datetime.Instant

data class Comment(
    val text: String,
    val author: Account,
    val postedAt: Instant,
)
