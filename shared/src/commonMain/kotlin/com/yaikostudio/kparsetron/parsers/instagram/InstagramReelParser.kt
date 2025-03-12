package com.yaikostudio.kparsetron.parsers.instagram

import com.yaikostudio.kparsetron.entities.ParsedSiteData
import com.yaikostudio.kparsetron.network.Downloader
import com.yaikostudio.kparsetron.network.NetworkHelperKtor
import com.yaikostudio.kparsetron.network.NetworkHelperKtor.Companion.getCookies
import com.yaikostudio.kparsetron.parsers.Parser
import com.yaikostudio.kparsetron.parsers.extensions.hasAnySegment
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.parseUrl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class InstagramReelParser(
    private val downloader: Downloader,
) : Parser() {
    @Serializable
    data class GeneralInfoData(
        @SerialName("shortcode") val shortcode: String,
        @SerialName("child_comment_count") val childCommentCount: Int,
        @SerialName("fetch_comment_count") val fetchCommentCount: Int,
        @SerialName("parent_comment_count") val parentCommentCount: Int,
        @SerialName("has_threaded_comments") val hasThreadedComments: Boolean,
    )

    override fun supportedHosts() = listOf(
        "www.instagram.com",
        "instagram.com",
    )

    private val API_BASE_URL = "https://i.instagram.com/api/v1"
    private val ENCODING_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"
    private val VALID_URL by lazy {
        """(?<url>https?://(?:www\.)?instagram\.com(?:/(?!share/)[^/?#]+)?/(?:p|tv|reels?(?!/audio/))/(?<id>[^/?#&]+))""".toRegex()
    }

    fun pkToId(mediaId: String): String {
        val pk = mediaId.split("_")[0].toLong()
        return encodeBaseN(pk, ENCODING_CHARS)
    }

    fun idToPk(shortcode: String): Long {
        val trimmedShortcode = if (shortcode.length > 28) shortcode.dropLast(28) else shortcode
        return decodeBaseN(trimmedShortcode, ENCODING_CHARS)
    }

    fun encodeBaseN(value: Long, table: String): String {
        val base = table.length
        var num = value
        val sb = StringBuilder()
        do {
            sb.append(table[(num % base).toInt()])
            num /= base
        } while (num > 0)
        return sb.reverse().toString()
    }

    fun decodeBaseN(input: String, table: String): Long {
        val base = table.length
        var num: Long = 0
        for (char in input) {
            num = num * base + table.indexOf(char)
        }
        return num
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

        val apiHeaders = mapOf(
            "X-IG-App-ID" to "936619743392459",
            "X-ASBD-ID" to "198387",
            "X-IG-WWW-Claim" to "0",
            "Origin" to "https://www.instagram.com",
            "Accept" to "*/*",
            "Content-Type" to "application/json; charset=UTF-8",
        )
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
