package com.yaikostudio.kparsetron.entities.network

import io.ktor.http.Url

sealed class ResourceUrl {
    data class Unprotected(
        val url: Url,
    ) : ResourceUrl()
}
