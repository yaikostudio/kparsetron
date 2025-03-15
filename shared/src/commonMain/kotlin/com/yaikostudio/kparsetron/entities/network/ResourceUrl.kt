package com.yaikostudio.kparsetron.entities.network

sealed class ResourceUrl {
    data class Unprotected(
        val url: String,
    ) : ResourceUrl()
}
