package com.crisolutions.commonlibrarysample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.crisolutions.commonlibrarysample.Samples.SampleJ
import com.crisolutions.commonlibrarysample.Samples.SampleKt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sample = SampleKt()
        val samplej = SampleJ()

        sample.stripNonDigitsSample()
        samplej.stripNonDigitsSample()
    }
}
