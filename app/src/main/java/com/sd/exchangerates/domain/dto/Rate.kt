package com.sd.exchangerates.domain.dto

import com.google.gson.annotations.SerializedName

data class Rate(
    @SerializedName("Valute")
    val valute: Map<String, Valute> = emptyMap()
)