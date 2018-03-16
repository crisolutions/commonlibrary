package com.crisolutions.commonlibrarysample.Samples;

import android.util.Log;

import com.crisolutions.commonlib.utils.StringUtils;

/**
 * Created by parshav on 3/16/18.
 */

public class SampleJ {

    public void stripNonDigitsSample() {
        String testString = "T1is 1s t35t";

        lprint("Initial String : " + testString);
        testString = StringUtils.stripNonDigits(testString);
        lprint("After Change : " + testString);
    }

    static void lprint(String output) {
        Log.d("Java Sample", output);
    }
}
