package com.crisolutions.commonlibraryktx

import android.os.Build
import android.text.Html

fun String.stripNonDigits(): String = replace(kotlin.text.Regex("[\\D]"), "")

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
