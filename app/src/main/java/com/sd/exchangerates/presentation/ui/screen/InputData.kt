package com.sd.exchangerates.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sd.exchangerates.R
import com.sd.exchangerates.presentation.ui.Routes
import com.sd.exchangerates.presentation.viewmodel.MainViewModel
import com.sd.exchangerates.utils.checkInputText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputData(
    vm: MainViewModel,
    navController: NavHostController,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {

    val summa = remember {
        mutableStateOf("")
    }
    val isErrorSumma = remember {
        mutableStateOf(false)
    }
    val currency = remember {
        mutableStateOf("")
    }
    val isErrorCurrency = remember {
        mutableStateOf(false)
    }

    val focusColorCurrency = remember {
        mutableStateOf(Color.Black)
    }

    val expanded = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            bitmap = ImageBitmap.imageResource(id = R.drawable.background),
            contentScale = ContentScale.FillBounds,
            contentDescription = "back"
        )
    }
    TopAppBar(
        title = {
            Text(text = "Ввод суммы", fontSize = 24.sp)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = summa.value,
            textStyle = TextStyle.Default.copy(fontSize = 28.sp, textAlign = TextAlign.Center),
            onValueChange = { newText ->
                summa.value = newText
            },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = {
                if (checkInputText(summa.value)) {
//                    if (text.value != vm.longWeekEndYear.value) {
//                        vm.setLongWeekEndYear(text.value)
//                        vm.getListLongWeekEnd(text.value)
//                    }

                    isErrorSumma.value = false
                    keyboardController?.hide()
                    focusManager.clearFocus()
                } else {
                    isErrorSumma.value = true
                }
            }),
            placeholder = {
                Text(text = "Введите сумму", fontSize = 12.sp)
            },
            isError = isErrorSumma.value,
            supportingText = {
                if (isErrorSumma.value) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Введите целое положительное число ",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            label = {
                Text(text = "Сумма, руб", color = Color.Gray)
            },
            singleLine = true,
            leadingIcon = {
                if (isErrorSumma.value)
                    Icon(
                        Icons.Default.Warning,
                        "errorSumma",
                        tint = MaterialTheme.colorScheme.error
                    )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.Black,
                errorCursorColor = Color.Red
            ),
            //     shape = MaterialTheme.shapes.small.copy(bottomEnd = CornerSize(6.dp), bottomStart = CornerSize(6.dp))
        )

        Row {
            
            TextField(
                value = currency.value,
                textStyle = TextStyle.Default.copy(fontSize = 32.sp, textAlign = TextAlign.Center),
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                enabled = false,
                readOnly = false,
                //      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            keyboardActions = KeyboardActions(onDone = {
//                if (checkInputText(text.value)) {
////                    if (text.value != vm.longWeekEndYear.value) {
////                        vm.setLongWeekEndYear(text.value)
////                        vm.getListLongWeekEnd(text.value)
////                    }
//
//                    isError.value = false
//                    keyboardController?.hide()
//                    focusManager.clearFocus()
//                } else {
//                    isError.value = true
//                }
//            }),
                placeholder = {
                    Text(text = "Выберите валюту", fontSize = 12.sp)
                },
                isError = isErrorCurrency.value,
                supportingText = {
                    if (isErrorCurrency.value) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Выберите валюту",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                label = {
                    Text(text = "Валюта", color = Color.Gray)
                },
                //      singleLine = true,
                leadingIcon = {
                    if (isErrorCurrency.value)
                        Icon(
                            Icons.Default.Warning,
                            "errorCurrency",
                            tint = MaterialTheme.colorScheme.error
                        )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        //     AddCurrency(expanded = expanded, currency = currency)
                        focusColorCurrency.value = Color.White
                        expanded.value = true

//                    if (checkInputText(currency.value)) {
////                        if (text.value!=vm.longWeekEndYear.value) {
////                            vm.setLongWeekEndYear(text.value)
////                            vm.getListLongWeekEnd(text.value)
////                        }
//                        //           vm.getListLongWeekEnd(text.value)
//                        isErrorCurrency.value = false
//                        keyboardController?.hide()
//                        focusManager.clearFocus()
//                    } else {
//                        isErrorCurrency.value = true
//                    }
                    })
                    {
                        Icon(imageVector = Icons.Default.List, contentDescription = "list")
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.Black,
                    disabledContainerColor = focusColorCurrency.value,
                    disabledTrailingIconColor = Color.Gray,
                ),
            )
            AddCurrency(expanded = expanded, currency = currency, color = focusColorCurrency, error = isErrorCurrency)
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Yellow,
                contentColor = Color.Gray
            ),
            onClick = {
                when {
                    summa.value.isEmpty() -> isErrorSumma.value = true
                    currency.value.isEmpty() -> isErrorCurrency.value = true
                    else -> navController.navigate(Routes.Result.route)
                }
        }) {
            Text(text = "Change")
        }
    }
}