package com.yaikostudio.kparsetron.parsers.instagram

import com.yaikostudio.kparsetron.parsers.Parser

abstract class InstagramBaseParser : Parser() {
    private val ENCODING_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"
    protected val API_BASE_URL = "https://i.instagram.com/api/v1"

    protected val apiHeaders = mapOf(
        "X-IG-App-ID" to "936619743392459",
        "X-ASBD-ID" to "198387",
        "X-IG-WWW-Claim" to "0",
        "Origin" to "https://www.instagram.com",
        "Accept" to "*/*",
        "Content-Type" to "application/json; charset=UTF-8",
    )

    override fun supportedHosts() = listOf(
        "www.instagram.com",
        "instagram.com",
    )

    protected fun pkToId(mediaId: String): String {
        val pk = mediaId.split("_")[0].toLong()
        return encodeBaseN(pk, ENCODING_CHARS)
    }

    protected fun idToPk(shortcode: String): Long {
        val trimmedShortcode = if (shortcode.length > 28) shortcode.dropLast(28) else shortcode
        return decodeBaseN(trimmedShortcode, ENCODING_CHARS)
    }

    protected fun encodeBaseN(value: Long, table: String): String {
        val base = table.length
        var num = value
        val sb = StringBuilder()
        do {
            sb.append(table[(num % base).toInt()])
            num /= base
        } while (num > 0)
        return sb.reverse().toString()
    }

    protected fun decodeBaseN(input: String, table: String): Long {
        val base = table.length
        var num: Long = 0
        for (char in input) {
            num = num * base + table.indexOf(char)
        }
        return num
    }
}
