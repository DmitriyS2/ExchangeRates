package com.sd.exchangerates.domain.dto

data class Rates(
    val EUR: Double = BASE_DOUBLE,
    val GBP: Double = BASE_DOUBLE,
    val USD: Double = BASE_DOUBLE,
) {
    companion object{
        private const val BASE_DOUBLE = 0.0
    }
}