package com.sd.exchangerates.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sd.exchangerates.presentation.ui.screen.InputData
import com.sd.exchangerates.presentation.ui.screen.ResultScreen
import com.sd.exchangerates.presentation.ui.theme.ExchangeRatesTheme
import com.sd.exchangerates.presentation.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
      //      ExchangeRatesTheme {
                val navController = rememberNavController()
                val vm: MainViewModel = viewModel()
                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current

                Box(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.InputData.route
                    ) {
                        composable(Routes.InputData.route) {
                            InputData(vm, navController, keyboardController, focusManager)
                        }
                        composable(Routes.Result.route) {
                            ResultScreen(vm, navController)
                        }
                    }
                }
            }
        }
  //  }
}
sealed class Routes(val route: String) {
    data object InputData : Routes("inputData")
    data object Result : Routes("resultScreen")
}
