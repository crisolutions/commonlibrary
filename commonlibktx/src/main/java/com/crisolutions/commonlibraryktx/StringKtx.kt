package com.crisolutions.commonlibraryktx

import android.os.Build
import android.text.Html

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

fun String.capitalizeWords(): String {
    val pieces: List<String> = this.split(" ")

    val sb = StringBuilder()
    pieces.map {
        val capL = it.first().toUpperCase()
        val part = it.substring(1)
        sb.append("$capL$part").append(" ")
    }

    return sb.toString().trim()
}

fun String.convertFromHtml(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT).toString()
    } else {
        Html.fromHtml(this).toString()
    }
}