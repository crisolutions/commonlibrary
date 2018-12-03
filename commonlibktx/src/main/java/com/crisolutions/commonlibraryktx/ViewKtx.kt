package com.crisolutions.commonlibraryktx

import android.text.InputFilter
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import java.util.*

fun TextInputLayout.setTextInputLayoutError(error: String) {
    this.error = error
    if (error.isEmpty()) {
        isErrorEnabled = false
    }
}

fun View.setRippleBackground(shouldShowRippleBackground: Boolean) {
    if (shouldShowRippleBackground) {
        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        setBackgroundResource(outValue.resourceId)
    } else {
        background = null
    }
}

fun EditText.addInputFilter(inputFilter: InputFilter) {
    val originalInputFilters = filters
    val inputFilters = Arrays.copyOf(originalInputFilters, originalInputFilters.size + 1)
    inputFilters[inputFilters.size - 1] = inputFilter
    filters = inputFilters
}

fun View.setGone(boolean: Boolean) {
    this.isGone = boolean
}

fun View.setVisible(boolean: Boolean) {
    this.isVisible = boolean
}

@Deprecated(
        "This will be removed in the next release",
        ReplaceWith("Use isVisible instead", "androidx.core.view.isVisible"),
        DeprecationLevel.ERROR
)
fun View.setVisibility(isVisible: Boolean) {
    this.isVisible = isVisible
}
