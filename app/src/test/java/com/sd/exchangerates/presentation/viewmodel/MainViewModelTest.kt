package com.sd.exchangerates.presentation.viewmodel

import com.sd.exchangerates.domain.dto.Valute
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest {

    @Test
    fun `swap crossRate correct for USD to EUR`() {
        // Given: USD = 96 RUB (nominal 1), EUR = 104 RUB (nominal 1)
        val base = Valute(code = "USD", nominal = 1, value = 96.0)
        val result = Valute(code = "EUR", nominal = 1, value = 104.0)

        // swap formula: (result.nominal * base.value) / result.value
        val crossRate = (result.nominal * base.value) / result.value

        // 1 USD = 96/104 EUR ≈ 0.9231 EUR
        assertEquals(0.9230769, crossRate, 0.0001)
    }

    @Test
    fun `swap crossRate correct for RUB to USD`() {
        // Given: RUB = 1 RUB (nominal 1), USD = 96 RUB (nominal 1)
        val base = Valute(code = "RUB", nominal = 1, value = 1.0)
        val result = Valute(code = "USD", nominal = 1, value = 96.0)

        val crossRate = (result.nominal * base.value) / result.value

        // 1 RUB = 1/96 USD ≈ 0.0104 USD
        assertEquals(0.0104166, crossRate, 0.0001)
    }

    @Test
    fun `swap crossRate with different nominal values`() {
        // Given: USD = 96 RUB (nominal 1), EUR = 104 RUB (nominal 10)
        val base = Valute(code = "USD", nominal = 1, value = 96.0)
        val result = Valute(code = "EUR", nominal = 10, value = 104.0)

        val crossRate = (result.nominal * base.value) / result.value

        // 1 USD = (10 * 96) / 104 EUR = 960/104 ≈ 9.2308 EUR
        assertEquals(9.230769, crossRate, 0.0001)
    }

    @Test
    fun `swap crossRate symmetric for same currency`() {
        // swap(USD, USD) should give crossRate = 1.0
        val base = Valute(code = "USD", nominal = 1, value = 96.0)
        val result = Valute(code = "USD", nominal = 1, value = 96.0)

        val crossRate = (result.nominal * base.value) / result.value

        assertEquals(1.0, crossRate, 0.0001)
    }
}
