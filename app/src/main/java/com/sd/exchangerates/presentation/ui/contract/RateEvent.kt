package com.sd.exchangerates.presentation.ui.contract

import androidx.compose.ui.graphics.Color
import com.sd.exchangerates.domain.dto.Rate
import com.sd.exchangerates.domain.dto.Valute

sealed interface RateEvent {
    data object OnRetryClick : RateEvent
    data class OnChangeSumma(val newSumma: String) : RateEvent

    data class OnChangeResultValuteMenuVisibility(
        val isVisible: Boolean,
        val cleanEditText: Boolean = false
    ) :
        RateEvent

    data class OnChangeResultValute(val valute: Valute) : RateEvent


    data class OnChangeBaseValuteMenuVisibility(
        val isVisible: Boolean,
        val cleanEditText: Boolean = false
    ) :
        RateEvent

    data class OnChangeBaseValute(val valute: Valute) : RateEvent

    data object OnClickButtonChange : RateEvent

    data object OnClickBackFromResult : RateEvent

    data class OnChangeTextBaseValute(val newText: String) : RateEvent
    data class OnChangeTextResultValute(val newText: String) : RateEvent

    data object OnClickSwap : RateEvent
    data object OnClickDoneKeyboard : RateEvent
}