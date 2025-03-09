package com.yaikostudio.kparsetron

import com.yaikostudio.kparsetron.entities.ParsedSiteData
import com.yaikostudio.kparsetron.parsers.SupportedSites
import io.ktor.http.Url
import io.ktor.http.parseUrl

class Kparsetron {
    suspend fun parse(url: String): ParsedSiteData? {
        val hasScheme = url.startsWith("https://", ignoreCase = true) || url.startsWith("http://", ignoreCase = true)
        val withScheme = if (hasScheme) {
            url
        } else {
            "https://$url"
        }

        val parsed = parseUrl(withScheme) ?: return throw UnsupportedOperationException("Invalid URL: $withScheme")
        return parse(parsed)
    }

    suspend fun parse(url: Url): ParsedSiteData? {
        val parsers = SupportedSites.instance.getParsers(url.host) ?: return null

        return parsers.firstNotNullOfOrNull { it.parse(url) }
    }
}
