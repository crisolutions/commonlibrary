package com.crisolutions.commonlibrarysample.Samples;

import android.util.Log;

import com.crisolutions.commonlib.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    public void arrayToMultiLineStringSample() {
        List<String> list = new ArrayList<>();
        list.add("first");
        list.add("The second item");
        list.add("Final item, third . ");
        String output = StringUtils.arrayToMultiLineString(list);
        lprint("Output : " + output);
    }

    static void lprint(String output) {
        Log.d("Java Sample", output);
    }
}
