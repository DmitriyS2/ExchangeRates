package com.sd.exchangerates.presentation.ui.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sd.exchangerates.presentation.ui.Routes
import com.sd.exchangerates.presentation.ui.contract.RateEffect
import com.sd.exchangerates.presentation.ui.screen.ResultScreen
import com.sd.exchangerates.presentation.viewmodel.MainViewModel

@Composable
fun ResultFeature(
    vm: MainViewModel = hiltViewModel(),
    navController: NavHostController,
) {

    val state = vm.state.collectAsState()

    ResultScreen(
        state = state.value,
        onEvent = vm::handleEvent,
    )

    LaunchedEffect(Unit) {
        vm.effect.collect { effect ->
            when (effect) {
                RateEffect.NavigateToResult -> {}
                RateEffect.NavigateToInputData -> navController.navigate(Routes.InputData.route) {
                    popUpTo(Routes.Result.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }
}