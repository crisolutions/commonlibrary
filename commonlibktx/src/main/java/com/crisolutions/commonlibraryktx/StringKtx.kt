package com.crisolutions.commonlibraryktx

/**
 * Created by parshav on 3/16/18.
 */

fun String.stripNonDigits(): String {
    return this.replace(kotlin.text.Regex("[\\D]"), "")
}