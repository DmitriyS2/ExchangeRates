package com.sd.exchangerates.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class StringSeparateTest {

    @Test
    fun `separate positive number formats with grouping by 3`() {
        // Save and restore locale to make test deterministic
        val originalLocale = Locale.getDefault()
        try {
            Locale.setDefault(Locale.US)
            assertEquals("1,000", "1000".separate())
            assertEquals("12,345", "12345".separate())
            assertEquals("1,234,567", "1234567".separate())
        } finally {
            Locale.setDefault(originalLocale)
        }
    }

    @Test
    fun `separate zero returns zero`() {
        val originalLocale = Locale.getDefault()
        try {
            Locale.setDefault(Locale.US)
            assertEquals("0", "0".separate())
        } finally {
            Locale.setDefault(originalLocale)
        }
    }

    @Test
    fun `separate non-number returns original string`() {
        assertEquals("abc", "abc".separate())
        assertEquals("", "".separate())
    }

    @Test
    fun `separate double formats with decimal separator`() {
        val originalLocale = Locale.getDefault()
        try {
            Locale.setDefault(Locale.US)
            assertEquals("1,234.56", 1234.56.separate())
            assertEquals("0", 0.0.separate())
        } finally {
            Locale.setDefault(originalLocale)
        }
    }

    @Test
    fun `separate preserves non-numeric strings`() {
        // Even with different locale, non-numeric strings pass through unchanged
        val originalLocale = Locale.getDefault()
        try {
            Locale.setDefault(Locale.US)
            assertEquals("12abc34", "12abc34".separate())
        } finally {
            Locale.setDefault(originalLocale)
        }
    }
}
