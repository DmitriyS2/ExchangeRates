package com.sd.exchangerates.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sd.exchangerates.R
import com.sd.exchangerates.domain.enums.Currency
import com.sd.exchangerates.presentation.ui.Routes
import com.sd.exchangerates.presentation.viewmodel.MainViewModel

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
    val isErrorCurrency = remember {
        mutableStateOf(false)
    }
    val enumCurrency = remember {
        mutableStateOf(Currency.NONE)
    }
    val focusColorCurrency = remember {
        mutableStateOf(Color.Black)
    }
    val expanded = remember {
        mutableStateOf(false)
    }

    TopAppBar(
        title = {
            Text(text = stringResource(R.string.input_sum), fontSize = 24.sp)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            scrolledContainerColor = Color.Black,
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
                    isErrorSumma.value = false
                    keyboardController?.hide()
                    focusManager.clearFocus()
                } else {
                    isErrorSumma.value = true
                }
            }),
            placeholder = {
                Text(text = stringResource(R.string.enter_sum), fontSize = 12.sp)
            },
            isError = isErrorSumma.value,
            supportingText = {
                if (isErrorSumma.value) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.enter_right_number),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            label = {
                Text(text = stringResource(R.string.amount_rub), color = Color.Gray)
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
        )

        Row {
            TextField(
                value = if (enumCurrency.value == Currency.NONE) "" else enumCurrency.value.name,
                textStyle = TextStyle.Default.copy(fontSize = 32.sp, textAlign = TextAlign.Center),
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                enabled = false,
                readOnly = false,
                placeholder = {
                    Text(text = stringResource(R.string.choose_currency), fontSize = 12.sp)
                },
                isError = isErrorCurrency.value,
                supportingText = {
                    if (isErrorCurrency.value) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.choose_currency),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                label = {
                    Text(text = stringResource(R.string.currency), color = Color.Gray)
                },
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
                        focusColorCurrency.value = Color.White
                        expanded.value = true
                    })
                    {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "list"
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    disabledTextColor = Color.White,
                    disabledContainerColor = focusColorCurrency.value,
                    disabledTrailingIconColor = Color.Gray,
                ),
            )
            AddCurrency(
                expanded = expanded,
                enumCurrency = enumCurrency,
                color = focusColorCurrency,
                error = isErrorCurrency
            )
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
                    enumCurrency.value == Currency.NONE -> isErrorCurrency.value = true
                    else -> {
                        vm.getResult()
                        vm.setCurrency(enumCurrency.value)
                        vm.setSum(summa.value)
                        navController.navigate(Routes.Result.route)
                    }
                }
            }) {
            Text(text = stringResource(R.string.change), fontSize = 18.sp)
        }
    }
}

fun checkInputText(text: String): Boolean {
    if (text.isEmpty()) return false
    return try {
        val number: Long = text.toLong()
        number > 0
    } catch (e: Exception) {
        false
    }
}
