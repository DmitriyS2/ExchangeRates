package com.sd.exchangerates.presentation.ui.contract

sealed interface RateEffect {

    data object NavigateToResult: RateEffect
    data object NavigateToInputData: RateEffect
}