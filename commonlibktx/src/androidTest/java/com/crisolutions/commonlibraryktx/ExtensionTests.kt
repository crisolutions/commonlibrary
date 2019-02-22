package com.crisolutions.commonlibraryktx

import org.junit.Assert.*
import org.junit.Test

class ExtensionTests {

    @Test
    fun testFilterIf() {

        val name = "John"

        name.filterIf { it == "John" }?.let {
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

    @Test
    fun testTransform() {

        val genres = arrayOf("Rock music", "Classical music", "Pop music")

        assertEquals(
                "Classical Music",
                genres.first { it.first() == 'C' }.transform { it.capitalizeWords() }
        )
    }
}

