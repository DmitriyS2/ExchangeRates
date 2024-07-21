package com.sd.exchangerates.domain.model

data class RateModel(
    val loading: Boolean = false,
    val error: Boolean = false,
    val summaRub: String = BASE_STRING,
    val baseCurrent: String = BASE_STRING,
    val result: String = BASE_STRING,
    val currency: String = BASE_STRING,
    val date: String = BASE_STRING,
    val rate: Double = BASE_DOUBLE,
    val title: String = BASE_STRING,
    val nominal: Int = BASE_INT,
) {
    companion object {
        private const val BASE_DOUBLE = 0.0
        private const val BASE_LONG = 0L
        private const val BASE_INT = 0
        private const val BASE_STRING = ""
    }
}
