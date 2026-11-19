package com.sd.exchangerates.domain.model

import com.sd.exchangerates.domain.dto.Rate
import com.sd.exchangerates.domain.dto.Valute

data class RateResponse(
    val valutes: List<Valute> = emptyList()
)

fun Rate?.toRateResponse(): RateResponse? {
    return this?.let {
        RateResponse(valutes = valute.map { it.value })
    }
}