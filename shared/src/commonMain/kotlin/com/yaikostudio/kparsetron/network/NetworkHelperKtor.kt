package com.yaikostudio.kparsetron.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.cookies.cookies
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.Url

class NetworkHelperKtor(
    private val client: HttpClient,
) {
    companion object {
        val instance = NetworkHelperKtor(
            HttpClient(provideHttpClientEngine()) {
                this.followRedirects = true
                install(HttpCookies)
            },
        )

        suspend fun HttpResponse.getCookies() = instance.client.cookies(request.url)
    }

    suspend fun get(
        url: Url,
        httpRequestBuilder: HttpRequestBuilder.() -> Unit = {},
    ): HttpResponse {
        return client.get(url, httpRequestBuilder)
    }
}
