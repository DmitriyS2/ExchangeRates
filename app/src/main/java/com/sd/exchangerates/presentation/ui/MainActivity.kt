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
import androidx.compose.ui.res.imageResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sd.exchangerates.R
import com.sd.exchangerates.presentation.ui.features.InputDataFeature
import com.sd.exchangerates.presentation.ui.features.ResultFeature
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
                val vm: MainViewModel = hiltViewModel()

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
                            InputDataFeature(vm, navController)
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
                            ResultFeature(vm, navController)
                        }
                    }
                }
            }
        }
    }
}
