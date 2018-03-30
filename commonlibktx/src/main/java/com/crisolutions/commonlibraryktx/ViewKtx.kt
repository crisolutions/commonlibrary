package com.crisolutions.commonlibraryktx

import android.support.design.widget.TextInputLayout
import android.text.InputFilter
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import java.util.*

fun TextInputLayout.setTextInputLayoutError(error: String) {
    this.error = error
    if (error.isEmpty()) {
        this.isErrorEnabled = false
    }
}

fun View.setRippleBackground(shouldShowRippleBackground: Boolean) {
    if (shouldShowRippleBackground) {
        val outValue = TypedValue()
        this.context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        this.setBackgroundResource(outValue.resourceId)
    } else {
        this.background = null
    }
}

fun EditText.addInputFilter(inputFilter: InputFilter) {
    val originalInputFilters = this.filters
    val inputFilters = Arrays.copyOf(originalInputFilters, originalInputFilters.size + 1)
    inputFilters[inputFilters.size - 1] = inputFilter
    this.filters = inputFilters
}
