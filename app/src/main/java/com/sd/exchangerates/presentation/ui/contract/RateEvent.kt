package com.sd.exchangerates.presentation.ui.contract

import androidx.compose.ui.graphics.Color
import com.sd.exchangerates.domain.dto.Rate
import com.sd.exchangerates.domain.dto.Valute

sealed interface RateEvent {
    data object OnRetryClick : RateEvent
    data class OnChangeSumma(val newSumma: String) : RateEvent
    data class OnChangeResultValuteMenuVisibility(val color: Color, val isVisible: Boolean) :
        RateEvent

    data class OnChangeResultValute(val valute: Valute, val color: Color) : RateEvent

    data class OnChangeBaseValuteMenuVisibility(val color: Color, val isVisible: Boolean) :
        RateEvent

    data class OnChangeBaseValute(val valute: Valute, val color: Color) : RateEvent

    data object OnClickButtonChange : RateEvent

    data object OnClickBackFromResult : RateEvent

    data class OnChangeTextBaseValute(val newText: String) : RateEvent
    data class OnChangeTextResultValute(val newText: String) : RateEvent
}