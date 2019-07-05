package com.crisolutions.commonlibraryktx

/**
 * Not sure what other class to place these in.
 * */

// Continues the chain only if block is true.
@Deprecated(
        "Can use takeIf function from kotlin stdlib instead.",
        replaceWith = ReplaceWith("takeIf")
)
fun <T> T.filterIf(block: (T) -> Boolean): T? = if (block.invoke(this)) this else null

// Map but for the entire object
// Can be used for accessing Utility functions within the chain.
@Deprecated(
        "Can use let function from kotlin stdlib instead.",
        replaceWith = ReplaceWith("let")
)
fun <R, E> R.transform(block: (R) -> E): E = block.invoke(this)
