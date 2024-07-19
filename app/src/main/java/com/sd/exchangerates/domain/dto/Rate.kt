package com.sd.exchangerates.domain.dto

data class Rate(
    val base: String,
    val date: String,
    val disclaimer: String,
    val rates: Rates,
    val timestamp: Int
)