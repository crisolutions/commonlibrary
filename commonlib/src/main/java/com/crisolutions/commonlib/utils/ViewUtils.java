package com.crisolutions.commonlib.utils;

import android.text.InputFilter;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

import androidx.annotation.NonNull;

public final class ViewUtils {

    private ViewUtils() {
    }

    public static void setTextInputLayoutError(String error, @NonNull TextInputLayout layout) {
        layout.setError(error);
        if (StringUtils.isEmpty(error)) {
            layout.setErrorEnabled(false);
        }
    }

    public static void setRippleBackground(@NonNull View view, boolean shouldShowRippleBackground) {
        if (shouldShowRippleBackground) {
            final TypedValue outValue = new TypedValue();
            view.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            view.setBackgroundResource(outValue.resourceId);
        } else {
            view.setBackground(null);
        }
    }

    public static void addInputFilter(@NonNull EditText editView, @NonNull InputFilter inputFilter) {
        final InputFilter[] originalInputFilters = editView.getFilters();
        final InputFilter[] inputFilters = Arrays.copyOf(originalInputFilters, originalInputFilters.length + 1);
        inputFilters[inputFilters.length - 1] = inputFilter;
        editView.setFilters(inputFilters);
    }
}
