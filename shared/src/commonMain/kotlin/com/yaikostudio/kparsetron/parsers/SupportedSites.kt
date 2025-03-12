package com.yaikostudio.kparsetron.parsers

import com.yaikostudio.kparsetron.network.Downloader
import com.yaikostudio.kparsetron.parsers.instagram.InstagramReelParser
import com.yaikostudio.kparsetron.parsers.youtube.YoutubeDetailParser

class SupportedSites {
    companion object {
        val instance = SupportedSites()
    }

    private val supportedSitesMapping: List<Pair<String, List<Parser>>>

    private fun listOfParsers(vararg parsers: Parser): List<Pair<String, List<Parser>>> {
        return parsers.flatMap { p ->
            p.supportedHosts().map { it to p }
        }.groupBy { (host, _) -> host }
            .mapValues { (_, parsers) -> parsers.map { it.second } }
            .toList()
    }

    init {
        val downloader = Downloader()

        supportedSitesMapping = listOfParsers(
            YoutubeDetailParser(downloader),
            InstagramReelParser(downloader),
        )
    }

    fun getParsers(host: String) = supportedSitesMapping.find { (url, _) ->
        url.equals(host, ignoreCase = true)
    }?.second
}
