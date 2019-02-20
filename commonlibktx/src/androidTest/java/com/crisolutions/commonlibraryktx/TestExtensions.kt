package com.crisolutions.commonlibraryktx

import org.junit.Assert
import org.junit.Assert.assertTrue

fun fail() {
    assertTrue(false)
}

fun pass() {
    assertTrue(true)
}

fun assertBlock(block: () -> Boolean) {
    Assert.assertTrue(block.invoke())
}