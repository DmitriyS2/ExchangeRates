package com.sd.exchangerates.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sd.exchangerates.domain.model.RateModel
import com.sd.exchangerates.presentation.ui.Routes
import com.sd.exchangerates.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(vm: MainViewModel, navController: NavHostController) {

    val resultRate by vm.result.observeAsState()

    TopAppBar(
        title = {
            Text(text = "Результат")
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
                //        vm.getListLongWeekEnd("")
                navController.navigate(Routes.InputData.route)
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "backToInputData"
                )
            }
        }
    )

    when {
        resultRate?.loading == true -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
           //         CircularProgressIndicator()
                }
            }
        }

        resultRate?.error == true -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Что-то пошло не так...Попробуйте позже")
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Yellow,
                        contentColor = Color.Gray
                    ),
                    onClick = {
                        vm.getResult()
                    }) {
                    Text(text = "Retry")
                }
            }
        }

        else -> {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp),
                Alignment.Center
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Yellow),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${resultRate?.summaRub} ${resultRate?.baseCurrent}", fontSize = 22.sp,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )
                        //    Spacer(modifier = Modifier.height(20.dp))
                        Text(text = "=", fontSize = 24.sp)
                        //     Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "${resultRate?.result} ${resultRate?.currency}", fontSize = 22.sp,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )
                    }
                }
            }
        }
    }
}