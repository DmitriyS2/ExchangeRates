package com.sd.exchangerates.domain.dto

import com.google.gson.annotations.SerializedName
import com.sd.exchangerates.util.BASE_STRING

data class Rate(
    @SerializedName("Date")
    val date: String = BASE_STRING,
    @SerializedName("Valute")
    val valute: Map<String, Valute> = emptyMap()
)