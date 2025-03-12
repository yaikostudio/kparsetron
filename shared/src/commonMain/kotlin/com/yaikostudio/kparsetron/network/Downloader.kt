package com.yaikostudio.kparsetron.network

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import com.fleeksoft.ksoup.parser.Parser
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.Url

class Downloader {
    private fun HttpRequestBuilder.addHeaderIfNotPresent(
        name: String,
        value: String,
    ) {
        if (null == headers[name]) {
            header(name, value)
        }
    }

    suspend fun get(
        url: Url,
        referrer: Url? = null,
        headers: Map<String, String> = emptyMap(),
    ): HttpResponse {
        return NetworkHelperKtor.instance.get(
            url = url,
            httpRequestBuilder = {
                headers.forEach { (key, value) ->
                    header(key, value)
                }
                addHeaderIfNotPresent(
                    "Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
                )
                addHeaderIfNotPresent("Accept-Language", "en-us,en;q=0.5")
                addHeaderIfNotPresent("Upgrade-Insecure-Requests", "1")
                addHeaderIfNotPresent(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36"
                )
                if (null != referrer) {
                    addHeaderIfNotPresent("Referer", referrer.toString())
                }
            },
        )
    }

    suspend fun getAndParse(url: Url, referrer: Url?): Document {
        val response = get(url, referrer)

        return Ksoup.parse(
            html = response.bodyAsText(),
            parser = Parser.htmlParser(),
            baseUri = response.request.url.toString(), // finalUrl after redirects
        )
    }
}
