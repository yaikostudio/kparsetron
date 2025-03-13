package com.yaikostudio.kparsetron.parsers.instagram

import com.yaikostudio.kparsetron.entities.ParsedSiteData
import com.yaikostudio.kparsetron.network.Downloader
import com.yaikostudio.kparsetron.network.NetworkHelperKtor
import com.yaikostudio.kparsetron.network.NetworkHelperKtor.Companion.getCookies
import com.yaikostudio.kparsetron.parsers.extensions.hasAnySegment
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.parseUrl

class InstagramReelParser(
    private val downloader: Downloader,
) : InstagramBaseParser() {
    private val VALID_URL by lazy {
        """(?<url>https?://(?:www\.)?instagram\.com(?:/(?!share/)[^/?#]+)?/(?:p|tv|reels?(?!/audio/))/(?<id>[^/?#&]+))""".toRegex()
    }

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
        val matches = VALID_URL.find(url.toString()) ?: return null
        val videoId = matches.groups["id"]?.value ?: return null

        val pk = idToPk(videoId)
        println("videoId=$videoId, pk=$pk")
        val rulingContentUrl = parseUrl("$API_BASE_URL/web/get_ruling_for_content/?content_type=MEDIA&target_id=$pk") ?: return null

        val response = downloader.get(
            url = rulingContentUrl,
            headers = apiHeaders,
        )
        println("response=${response.getCookies()}")
        println("response=${NetworkHelperKtor.instance.getCookies(parseUrl("https://www.instagram.com")!!)}")

        val csrfToken = response.getCookies().find {
            it.name == "csrftoken"
        }?.value

        val variables = GeneralInfoData(
            shortcode = videoId,
            childCommentCount = 3,
            fetchCommentCount = 40,
            parentCommentCount = 24,
            hasThreadedComments = true,
        )
        val generalInfoUrl = URLBuilder("https://www.instagram.com/graphql/query/")
            .apply {
                parameters.append("doc_id", "8845758582119845")
                parameters.append("variables", json.encodeToString(variables))
            }
            .build()
        println("generalInfoUrl=$generalInfoUrl")

        val generalInfo = downloader.get(
            url = generalInfoUrl,
            referrer = url,
            headers = apiHeaders + mapOf(
                "X-CSRFToken" to (csrfToken ?: ""),
                "X-Requested-With" to "XMLHttpRequest",
            ),
        )
        println("generalInfo=${generalInfo.bodyAsText()}")

        return null
    }
}
