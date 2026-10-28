package com.sd.exchangerates.presentation.ui.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sd.exchangerates.presentation.ui.Routes
import com.sd.exchangerates.presentation.ui.contract.RateEffect
import com.sd.exchangerates.presentation.ui.screen.InputDataScreen
import com.sd.exchangerates.presentation.viewmodel.MainViewModel

@Composable
fun InputDataFeature(
    vm: MainViewModel = hiltViewModel(),
    navController: NavHostController,
) {

    val state = vm.state.collectAsState()
    val context = LocalContext.current

    InputDataScreen(
        state = state.value,
        onEvent = vm::handleEvent,
    )

    LaunchedEffect(Unit) {
        vm.effect.collect { effect ->
            when (effect) {
                RateEffect.NavigateToResult -> navController.navigate(Routes.Result.route)
                RateEffect.NavigateToInputData -> {}
            }
        }
    }
}