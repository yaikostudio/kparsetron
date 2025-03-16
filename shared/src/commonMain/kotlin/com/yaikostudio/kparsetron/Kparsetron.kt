package com.yaikostudio.kparsetron

import com.yaikostudio.kparsetron.entities.ParsedSiteData
import com.yaikostudio.kparsetron.parsers.SupportedSites
import io.ktor.http.Url
import io.ktor.http.parseUrl

class Kparsetron {
    suspend fun parse(url: String): Result<ParsedSiteData> {
        val hasScheme = url.startsWith("https://", ignoreCase = true) || url.startsWith("http://", ignoreCase = true)
        val withScheme = if (hasScheme) {
            url
        } else {
            "https://$url"
        }

        val parsed = parseUrl(withScheme) ?: return Result.failure(UnsupportedOperationException("Invalid URL: $withScheme"))
        return parse(parsed)
    }

    suspend fun parse(url: Url): Result<ParsedSiteData> {
        val parsers = SupportedSites.instance.getParsers(url.host) ?: emptyList()

        val parser = parsers.firstOrNull { it.supports(url) }
        parser ?: return Result.failure(UnsupportedOperationException("No parser found for URL: $url"))
        try {
            return parser.parse(url)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
