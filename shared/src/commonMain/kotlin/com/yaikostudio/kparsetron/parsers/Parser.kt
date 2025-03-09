package com.yaikostudio.kparsetron.parsers

import com.yaikostudio.kparsetron.entities.ParsedSiteData
import io.ktor.http.Url

interface Parser {
    suspend fun parse(url: Url): ParsedSiteData?
}
