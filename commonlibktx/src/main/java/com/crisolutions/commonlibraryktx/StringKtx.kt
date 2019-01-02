package com.crisolutions.commonlibraryktx

import android.os.Build
import android.text.Html

fun String.stripNonDigits(): String = replace(kotlin.text.Regex("[\\D]"), "")

@Deprecated(
        "This will be removed in the next release",
        ReplaceWith("Use joinToString from Kotlin stdlib instead", "kotlin.collections.joinToString"),
        DeprecationLevel.ERROR
)
fun Array<String>.arrayToMultiLineString(): String {
    return joinToString("\n")
}

fun String.capitalizeWords(): String {
    val pieces: List<String> = split(" ")

    val sb = StringBuilder()
    pieces.map {
        val capL = it.first().toUpperCase()
        val part = it.substring(1)
        sb.append("$capL$part").append(" ")
    }

    return sb.toString().trim()
}

fun String.convertFromHtml(): String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT).toString()
} else {
    @Suppress("DEPRECATION")
    Html.fromHtml(this).toString()
}
