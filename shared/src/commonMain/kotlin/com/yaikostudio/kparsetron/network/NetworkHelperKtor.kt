package com.yaikostudio.kparsetron.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.cookies.cookies
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
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
                install(ContentEncoding)
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) = println(message)
                    }
                    level = LogLevel.ALL
                }
            },
        )

        suspend fun HttpResponse.getCookies() = instance.getCookies(request.url)
    }

    suspend fun getCookies(url: Url) = client.cookies(url)

    suspend fun get(
        url: Url,
        httpRequestBuilder: HttpRequestBuilder.() -> Unit = {},
    ): HttpResponse {
        return client.get(url, httpRequestBuilder)
    }
}
