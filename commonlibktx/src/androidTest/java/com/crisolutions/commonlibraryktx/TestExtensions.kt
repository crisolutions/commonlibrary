package com.crisolutions.commonlibraryktx

import org.junit.Assert

fun fail() {
    Assert.assertTrue(false)
}

fun assertBlock(block: () -> Boolean) {
    Assert.assertTrue(block.invoke())
}