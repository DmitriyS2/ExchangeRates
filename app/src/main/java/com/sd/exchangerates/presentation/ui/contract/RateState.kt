package com.sd.exchangerates.presentation.ui.contract

import androidx.compose.ui.graphics.Color
import com.sd.exchangerates.domain.dto.Valute
import com.sd.exchangerates.domain.dto.rub
import com.sd.exchangerates.util.BASE_STRING

data class RateState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val allCurrency: List<Valute> = emptyList(),
    val allBaseCurrency: List<Valute> = emptyList(),
    val allResultCurrency: List<Valute> = emptyList(),
    val textBaseValute: String = BASE_STRING,
    val textResultValute: String = BASE_STRING,
    val isErrorAmount: Boolean = false,
    val isErrorBaseCurrency: Boolean = false,
    val isErrorResultCurrency: Boolean = false,
    val baseAmount: String = BASE_STRING,
    val resultAmount: String = BASE_STRING,
    val baseValute: Valute = rub,
    val resultValute: Valute = rub,

 //   val baseCodeCurrency: String = BASE_CURRENT,
 //   val resultCodeCurrency: String = BASE_STRING,
    val date: String = BASE_STRING,
 //   val baseRate: Double = BASE_DOUBLE,
 //   val resultRate: Double = BASE_DOUBLE,
    val crossRate: Double = 1.0,
 //   val title: String = BASE_STRING,
 //   val nominal: Int = BASE_INT,
    val isResultValuteMenuVisible: Boolean = false,
    val isBaseValuteMenuVisible: Boolean = false,
    val focusColorBaseCurrency: Color = Color.Black,
    val focusColorResultCurrency: Color = Color.Black,
)