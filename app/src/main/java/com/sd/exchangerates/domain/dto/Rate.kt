package com.sd.exchangerates.domain.dto

data class Rate(
    val base: String = BASE_STRING,
    val date: String = BASE_STRING,
    val rates: Rates = Rates(),
) {
    companion object {
        private const val BASE_STRING = ""
    }
}