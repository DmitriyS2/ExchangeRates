package com.sd.exchangerates.presentation.ui

sealed class Routes(val route: String) {
    data object InputData : Routes("inputDataScreen")
    data object Result : Routes("resultScreen")
}