package com.sd.exchangerates.domain.model

import com.sd.exchangerates.domain.dto.Rate
import com.sd.exchangerates.domain.dto.Valute
import com.sd.exchangerates.util.BASE_STRING
import com.sd.exchangerates.util.toLocalDate

data class RateResponse(
    val date: String = BASE_STRING,
    val valutes: List<Valute> = emptyList()
)

fun Rate?.toRateResponse(): RateResponse? {
    return this?.let {
        RateResponse(
            date = date.toLocalDate(),
            valutes = valute.map { it.value }
        )
    }
}