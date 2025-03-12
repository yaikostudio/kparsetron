package com.yaikostudio.kparsetron.parsers.instagram

import com.yaikostudio.kparsetron.entities.ParsedSiteData
import com.yaikostudio.kparsetron.network.Downloader
import com.yaikostudio.kparsetron.parsers.Parser
import com.yaikostudio.kparsetron.parsers.extensions.hasAnySegment
import io.ktor.http.Url

class InstagramReelParser(
    private val downloader: Downloader,
) : Parser() {
    override fun supportedHosts() = listOf(
        "www.instagram.com",
        "instagram.com",
    )

    override suspend fun supports(url: Url): Boolean {
        if (url.host !in listOf("www.instagram.com", "instagram.com")) {
            return false
        }

        if (url.hasAnySegment(0..1, "p", "tv", "reel")) {
            return true
        }

        return false
    }

    override suspend fun parse(url: Url): ParsedSiteData? {
        return null
    }
}
