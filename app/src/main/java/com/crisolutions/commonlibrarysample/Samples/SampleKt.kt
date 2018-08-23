package com.crisolutions.commonlibrarysample.Samples

import android.util.Log
import com.crisolutions.commonlibraryktx.arrayToMultiLineString
import com.crisolutions.commonlibraryktx.capitalizeWords
import com.crisolutions.commonlibraryktx.stripNonDigits

/**
 * Created by parshav on 3/16/18.
 */

class SampleKt {

    fun stripNonDigitsSample() {
        var testString: String = "T1is 1s t35t"

        lprint(testString)
        testString = testString.stripNonDigits()
        lprint("After Change : $testString")
    }

    fun arrayToMultiLineStringSample() {
        val list: Array<String> = arrayOf("first","The second item", "Final item, third . ")
        val output = list.arrayToMultiLineString()
        lprint("Output: $output")
    }

    fun capitalizeWords() {
        var testString = "tjis is the Test thingy yo asd s"
        testString = testString.capitalizeWords()
        lprint(testString)
    }
}

fun lprint(data: String) = Log.d("Ktx Sample", data)