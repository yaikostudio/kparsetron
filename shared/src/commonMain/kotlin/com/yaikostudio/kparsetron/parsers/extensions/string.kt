package com.yaikostudio.kparsetron.parsers.extensions

fun String.parseLongFromNumberWithUnit(): Long {
    val number = this.lowercase().replace(",", ".")
    return when {
        number.endsWith("k") -> (number.dropLast(1).toDouble() * 1000).toLong()
        number.endsWith("m") -> (number.dropLast(1).toDouble() * 1000000).toLong()
        number.endsWith("b") -> (number.dropLast(1).toDouble() * 1000000000).toLong()
        else -> number.toLong()
    }
}
