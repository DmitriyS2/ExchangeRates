package com.sd.exchangerates.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.navigation.NavHostController
import com.sd.exchangerates.R
import com.sd.exchangerates.presentation.viewmodel.MainViewModel

@Composable
fun InputData(vm: MainViewModel, navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(modifier = Modifier.fillMaxSize() ,bitmap = ImageBitmap.imageResource(id = R.drawable.background), contentScale = ContentScale.FillBounds, contentDescription = "back")
    }
    Column(modifier = Modifier.fillMaxSize()) {

    }
}