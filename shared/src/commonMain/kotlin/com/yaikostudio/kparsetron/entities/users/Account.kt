package com.yaikostudio.kparsetron.entities.users

import com.yaikostudio.kparsetron.entities.network.ResourceUrl

data class Account(
    val id: String,
    val name: String?,
    val username: String,
    val isVerified: Boolean,
    val profilePicUrl: ResourceUrl?,
    val followers: Long?,
)
