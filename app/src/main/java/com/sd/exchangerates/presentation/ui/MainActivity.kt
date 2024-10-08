package com.sd.exchangerates.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.imageResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sd.exchangerates.R
import com.sd.exchangerates.presentation.ui.screen.InputData
import com.sd.exchangerates.presentation.ui.screen.ResultScreen
import com.sd.exchangerates.presentation.ui.theme.ExchangeRatesTheme
import com.sd.exchangerates.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExchangeRatesTheme {
                val navController = rememberNavController()
                val vm: MainViewModel = viewModel()
                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current

                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        bitmap = ImageBitmap.imageResource(id = R.drawable.background),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "background"
                    )
                    NavHost(
                        navController = navController,
                        startDestination = Routes.InputData.route,
                    ) {
                        composable(
                            Routes.InputData.route,
                            enterTransition = {
                                slideIntoContainer(
                                    animationSpec = tween(300, easing = EaseIn),
                                    towards = AnimatedContentTransitionScope.SlideDirection.End
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    animationSpec = tween(300, easing = EaseIn),
                                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                                )
                            },
                        ) {
                            InputData(vm, navController, keyboardController, focusManager)
                        }
                        composable(
                            Routes.Result.route,
                            enterTransition = {
                                slideIntoContainer(
                                    animationSpec = tween(300, easing = EaseIn),
                                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    animationSpec = tween(300, easing = EaseIn),
                                    towards = AnimatedContentTransitionScope.SlideDirection.End
                                )
                            },
                        ) {
                            ResultScreen(vm, navController)
                        }
                    }
                }
            }
        }
    }
}

sealed class Routes(val route: String) {
    data object InputData : Routes("inputData")
    data object Result : Routes("resultScreen")
}
