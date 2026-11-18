package com.sd.exchangerates.data.network

import com.google.gson.annotations.SerializedName
import com.sd.exchangerates.domain.dto.Valute
import com.sd.exchangerates.util.BASE_STRING

data class RateDTO(
    @SerializedName("Date")
    val date: String = BASE_STRING,
    @SerializedName("Valute")
    val valute: Map<String, Valute> = emptyMap()
)
