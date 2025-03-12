package com.yaikostudio.kparsetron.parsers

import com.yaikostudio.kparsetron.entities.ParsedSiteData
import io.ktor.http.Url
import kotlinx.serialization.json.Json

abstract class Parser {
    abstract fun supportedHosts(): List<String>
    abstract suspend fun supports(url: Url): Boolean
    abstract suspend fun parse(url: Url): ParsedSiteData?

    protected val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
}
