package com.yaikostudio.kparsetron.parsers.extensions

import kotlinx.serialization.json.Json

inline fun <reified T> Json.findJson(
    regex: Regex,
    input: String,
): T? {
    val match = regex.find(input) ?: return null
    val jsonString = match.groupValues.getOrNull(1) ?: return null

    return decodeFromString<T>(jsonString)
}
