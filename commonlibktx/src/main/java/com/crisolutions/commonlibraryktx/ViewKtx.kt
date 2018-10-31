package com.crisolutions.commonlibraryktx

import android.text.InputFilter
import android.util.TypedValue
import android.view.View
import android.widget.EditText
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

@Deprecated(
        "This will be removed in the next release",
        ReplaceWith("Use isVisible instead", "androidx.core.view.isVisible"),
        DeprecationLevel.ERROR
)
fun View.setVisibility(isVisible: Boolean) {
    this.isVisible = isVisible
}

fun View.setVisibility(vv: ViewVisibility) {
    visibility = when (vv) {
        ViewVisibility.VISIBLE -> View.VISIBLE
        ViewVisibility.INVISIBLE -> View.INVISIBLE
        ViewVisibility.GONE -> View.GONE
    }
}

enum class ViewVisibility {
    VISIBLE, INVISIBLE, GONE
}
