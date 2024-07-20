package com.sd.exchangerates.domain.model

import com.sd.exchangerates.domain.dto.Rate

data class RateModel(
    val loading: Boolean = false,
   // val rate: Rate = Rate(),
    val error: Boolean = false,
    val summaRub:Long = 0L,
    val baseCurrent:String = "",
    val result:Double = 0.0,
    val currency:String = "",
)
