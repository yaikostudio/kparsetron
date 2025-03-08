package com.yaikostudio.kparsetron.data.network

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import com.fleeksoft.ksoup.parser.Parser
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.Url

class Downloader {
    suspend fun download(url: Url, referrer: Url?): Document {
        val response = NetworkHelperKtor.instance.get(
            url = url,
            httpRequestBuilder = {
                header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                header("Accept-Language", "en-us,en;q=0.5")
                header("Upgrade-Insecure-Requests", "1")
                header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36")
                if (null != referrer) {
                    header("Referer", referrer)
                }
            },
        )

        // Final URL after redirects
        val finalUrl = response.request.url.toString()

        return Ksoup.parse(
            html = response.bodyAsText(),
            parser = Parser.htmlParser(),
            baseUri = finalUrl,
        )
    }
}
