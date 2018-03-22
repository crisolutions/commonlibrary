package com.crisolutions.commonlibraryktx

import android.widget.TextView

/**
 * Created by Parshav on 3/22/18.
 */

class TextKtx {
    companion object {
        val DOT: Char = '\u2022'
    }
}
fun TextView.getTextWidth(): Int {
    this.measure(0, 0)
    return this.measuredWidth
}

fun String.maskText(visibleChars: Int) = this.maskText(visibleChars, TextKtx.DOT)

fun String.maskText(visibleChars: Int, maskCharacter: Char) = this.maskText(visibleChars, maskCharacter, -1)

fun String.maskText(visibleChars: Int, maxLength: Int) = this.maskText(visibleChars, TextKtx.DOT, maxLength)

fun String.maskText(visibleChars: Int, maskCharacter: Char, maxLength: Int): CharSequence {
    return when {
        this.length <= visibleChars -> this
        maxLength != -1 && maxLength < visibleChars -> throw IllegalArgumentException("Cannot have a max length be shorter than the visible character count")
        else -> MaskedCharSequence(this, visibleChars, maskCharacter, maxLength)
    }
}

class MaskedCharSequence(
        val original: CharSequence,
        val visibleLength: Int,
        val maskedChar: Char,
        val maxLength: Int
    ) : CharSequence {

    var resolvedLength = if (maxLength != -1) Math.min(maxLength, original.length) else original.length

    override val length: Int
        get() = resolvedLength

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        throw IllegalArgumentException("Cannot sub-sequence masked text")
    }

    override fun get(index: Int): Char {
        return if (index < resolvedLength - visibleLength) {
            maskedChar
        } else {
            original[resolveIndex(index)]
        }
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