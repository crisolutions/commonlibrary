package com.crisolutions.commonlibraryktx

import android.widget.TextView

object TextTools {
    const val DOT = '\u2022'
}

fun TextView.getTextWidth(): Int {
    measure(0, 0)
    return measuredWidth
}

fun String.maskText(visibleChars: Int) = maskText(visibleChars, TextTools.DOT)

fun String.maskText(visibleChars: Int, maskCharacter: Char) = maskText(visibleChars, maskCharacter, -1)

fun String.maskText(visibleChars: Int, maxLength: Int) = maskText(visibleChars, TextTools.DOT, maxLength)

fun String.maskText(visibleChars: Int, maskCharacter: Char, maxLength: Int): CharSequence = when {
    length <= visibleChars -> this
    maxLength != -1 && maxLength < visibleChars -> throw IllegalArgumentException("Cannot have a max length be shorter than the visible character count")
    else -> MaskedCharSequence(this, visibleChars, maskCharacter, maxLength)
}


internal class MaskedCharSequence(
        private val original: CharSequence,
        private val visibleLength: Int,
        private val maskedChar: Char,
        maxLength: Int
) : CharSequence {

    private var resolvedLength = if (maxLength != -1) Math.min(maxLength, original.length) else original.length

    override val length: Int
        get() = resolvedLength

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        throw IllegalArgumentException("Cannot sub-sequence masked text")
    }

    override fun get(index: Int): Char = if (index < resolvedLength - visibleLength) {
        maskedChar
    } else {
        original[resolveIndex(index)]
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (i in 0 until resolvedLength) {
            sb.append(get(i))
        }
        return sb.toString()
    }

    private fun resolveIndex(requestedIndex: Int) = requestedIndex + (original.length - resolvedLength)
}
