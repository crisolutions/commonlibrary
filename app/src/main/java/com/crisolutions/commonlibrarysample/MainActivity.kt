package com.crisolutions.commonlibrarysample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.crisolutions.commonlibrarysample.Samples.SampleKt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sampleK = SampleKt()

        //sample.stripNonDigitsSample()
        //samplej.stripNonDigitsSample()

        //sampleK.arrayToMultiLineStringSample()
        //sampleJ.arrayToMultiLineStringSample()

        sampleK.capitalizeWords()
    }
}
