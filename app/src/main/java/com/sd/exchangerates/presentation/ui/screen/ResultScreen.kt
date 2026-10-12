package com.sd.exchangerates.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sd.exchangerates.R
import com.sd.exchangerates.presentation.ui.Routes
import com.sd.exchangerates.presentation.viewmodel.MainViewModel
import com.sd.exchangerates.util.separate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(vm: MainViewModel, navController: NavHostController) {

    val state by vm.state.collectAsState()

    TopAppBar(
        title = {
            Text(text = stringResource(R.string.result))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            scrolledContainerColor = Color.Black,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(Routes.InputData.route)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "backToInputData"
                )
            }
        }
    )

    when {
        state.loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    CircularProgressIndicator(color = Color.Yellow)
                }
            }
        }

        state.error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.try_again_later), color = Color.White)
                Button(
                    modifier = Modifier.padding(top = 30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Yellow,
                        contentColor = Color.Gray
                    ),
                    onClick = {
                        vm.getData()
                    }) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        }

        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp),
                Alignment.Center
            ) {
                state.let {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Yellow),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${it.summaRub.separate()} ${it.baseCurrent}",
                                fontSize = 22.sp,
                                modifier = Modifier.padding(vertical = 20.dp)
                            )
                            Text(text = "=", fontSize = 24.sp)
                            Text(
                                text = "${it.result} ${it.currency}",
                                fontSize = 22.sp,
                                modifier = Modifier.padding(vertical = 20.dp)
                            )
                            Text(
                                text = stringResource(
                                    R.string.rate_on_date,
                                    it.date,
                                    it.rate,
                                    it.baseCurrent,
                                    it.nominal,
                                    it.title
                                ),
                                fontSize = 8.sp,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}