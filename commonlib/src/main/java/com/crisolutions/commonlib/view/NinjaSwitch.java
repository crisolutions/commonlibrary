package com.crisolutions.commonlib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * Android programmatically checkable widgets, differentiate between user clicks & programmatic setChecked calls.
 * Based on this answer on StackOverflow: http://stackoverflow.com/a/14307643/570930.
 * There are times when setting a switch to ON results in some action (Save to DB/ perform an HTTP POST etc).
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public final class NinjaSwitch extends Switch implements ProgrammaticallyCheckable {

    private OnCheckedChangeListener mListener = null;

    public NinjaSwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public NinjaSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NinjaSwitch(Context context) {
        super(context);
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        if (this.mListener == null) {
            this.mListener = listener;
        }
        super.setOnCheckedChangeListener(listener);
    }

    @Override
    public void setCheckedProgrammatically(boolean checked) {
        super.setOnCheckedChangeListener(null);
        super.setChecked(checked);
        super.setOnCheckedChangeListener(mListener);
    }
}