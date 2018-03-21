package com.crisolutions.commonlibraryktx

/**
 * Created by parshav on 3/16/18.
 */

fun String.stripNonDigits(): String {
    this.isNotEmpty()
    return this.replace(kotlin.text.Regex("[\\D]"), "")
}

fun Array<String>.arrayToMultiLineString(): String {
    if (this.isEmpty()) {
        return ""
    }

    val sb = StringBuilder()
    this.map {
        sb.append(it).append("\n")
    }
    return sb.toString().trim()
}