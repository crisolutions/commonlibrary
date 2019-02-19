package com.crisolutions.commonlibraryktx

import org.junit.Assert.assertTrue
import org.junit.Test

class ExtensionTests {

    @Test
    fun testFilterIf() {

        val name = "John"

        name.filterIf { it == "Johns" }?.let {
            assertTrue(it == "John")
        } ?: fail()
    }

    @Test
    fun testFilterIfFailure() {
        val lastName = "Doe"

        lastName
                .map { it.toUpperCase() }
                .joinToString()
                .replace(",", "")
                .replace(" ", "")
                .filterIf { it == "DOE" }?.let {
                    assertTrue(it == "DOE")
                } ?: fail()
    }

}

fun fail() {
    assertTrue(false)
}