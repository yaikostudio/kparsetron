package com.yaikostudio.kparsetron.parsers.extensions

import io.ktor.http.Url
import io.ktor.http.parseUrl

fun Url.hasSegment(inPositions: IntRange, segment: String) = segment in segments.slice(inPositions)
fun Url.hasAnySegment(inPositions: IntRange, vararg segments: String) = segments.any { hasSegment(inPositions, it) }

fun String.toUrl(): Url = parseUrl(this)!!
