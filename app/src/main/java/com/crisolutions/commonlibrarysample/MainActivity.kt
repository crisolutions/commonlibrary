package com.crisolutions.commonlibrarysample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.crisolutions.commonlibrarysample.Samples.SampleJ
import com.crisolutions.commonlibrarysample.Samples.SampleKt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sampleK = SampleKt()
        val sampleJ = SampleJ()

        //sample.stripNonDigitsSample()
        //samplej.stripNonDigitsSample()

        sampleK.arrayToMultiLineStringSample()
        sampleJ.arrayToMultiLineStringSample()
    }
}
