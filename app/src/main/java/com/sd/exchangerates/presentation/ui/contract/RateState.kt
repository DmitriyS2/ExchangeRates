package com.sd.exchangerates.presentation.ui.contract

import androidx.compose.ui.graphics.Color
import com.sd.exchangerates.domain.dto.Valute
import com.sd.exchangerates.util.BASE_CURRENT
import com.sd.exchangerates.util.BASE_DOUBLE
import com.sd.exchangerates.util.BASE_INT
import com.sd.exchangerates.util.BASE_STRING

data class RateState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val allCurrency: List<Valute> = emptyList(),
    val isErrorSumma: Boolean = false,
    val isErrorCurrency: Boolean = false,
    val summaRub: String = BASE_STRING,
    val baseCurrent: String = BASE_CURRENT,
    val result: String = BASE_STRING,
    val currency: String = BASE_STRING,
    val date: String = BASE_STRING,
    val rate: Double = BASE_DOUBLE,
    val title: String = BASE_STRING,
    val nominal: Int = BASE_INT,
    val isValuteMenuVisible: Boolean = false,
    val focusColorCurrency: Color = Color.Companion.Black,
)