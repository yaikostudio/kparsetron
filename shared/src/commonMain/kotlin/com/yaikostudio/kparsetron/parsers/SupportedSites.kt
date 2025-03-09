package com.yaikostudio.kparsetron.parsers

import com.yaikostudio.kparsetron.network.Downloader
import com.yaikostudio.kparsetron.parsers.youtube.YoutubeDetailParser

class SupportedSites {
    companion object {
        val instance = SupportedSites()
    }

    private val supportedSitesMapping: List<Pair<String, List<Parser>>>

    init {
        val downloader = Downloader()

        supportedSitesMapping = listOf(
            "youtube.com" to listOf(YoutubeDetailParser(downloader)),
            "www.youtube.com" to listOf(YoutubeDetailParser(downloader)),
            "youtu.be" to listOf(YoutubeDetailParser(downloader)),
        )
    }

    fun getParsers(host: String) = supportedSitesMapping.find { (url, _) ->
        url.equals(host, ignoreCase = true)
    }?.second
}
