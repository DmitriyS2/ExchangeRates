package com.sd.exchangerates.presentation.ui.contract

import androidx.compose.ui.graphics.Color
import com.sd.exchangerates.domain.dto.Valute

sealed interface RateEvent {
    data object OnRetryClick : RateEvent
    data class OnChangeSumma(val newSumma: String) : RateEvent
    data class OnChangeValuteMenuVisibility(val color: Color, val isVisible: Boolean) : RateEvent
    data class OnChangeValute(val valute: Valute, val color: Color): RateEvent
    data object OnClickButtonChange: RateEvent

    data object OnClickBackFromResult: RateEvent
}