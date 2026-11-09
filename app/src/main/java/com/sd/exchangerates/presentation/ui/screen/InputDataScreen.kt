package com.sd.exchangerates.presentation.ui.screen

import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sd.exchangerates.R
import com.sd.exchangerates.presentation.ui.components.AddBaseCurrency
import com.sd.exchangerates.presentation.ui.components.AddResultCurrency
import com.sd.exchangerates.presentation.ui.contract.RateEvent
import com.sd.exchangerates.presentation.ui.contract.RateState
import com.sd.exchangerates.presentation.viewmodel.MainViewModel
import com.sd.exchangerates.util.noActions
import com.sd.exchangerates.util.separate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDataScreen(
    state: RateState,
    onEvent: (RateEvent) -> Unit,
) {
    val vm: MainViewModel = hiltViewModel()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

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

    when {
        state.loading -> LoadingScreen()

        state.error -> ErrorScreen { onEvent(RateEvent.OnRetryClick) }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Что меняем:", color = Color.White)
                TextField(
                    value = state.baseAmount,
                    textStyle = TextStyle.Default.copy(
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center
                    ),
                    onValueChange = { newText ->
                        onEvent(RateEvent.OnChangeSumma(newText))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = {
               //         vm.separateAmount()
                        Log.d("MyLog", "click")
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                    placeholder = {
                        Text(text = stringResource(R.string.enter_sum), fontSize = 12.sp)
                    },
                    isError = state.isErrorAmount,
                    supportingText = {
                        if (state.isErrorAmount) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.enter_right_number),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    label = {
                        Text(text = stringResource(R.string.amount), color = Color.Gray)
                    },
                    suffix = {
                        //            Text(text = state.baseCurrent)
                    },
                    singleLine = true,
                    leadingIcon = {
                        if (state.isErrorAmount)
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

// BASE VALUTE
                Row {
                    TextField(
                        //      value = state.baseValute.code,
                        value = state.textBaseValute,
                        textStyle = TextStyle.Default.copy(
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        ),
                        onValueChange = { newText ->
                            onEvent(RateEvent.OnChangeTextBaseValute(newText))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        //       enabled = false,
                        enabled = true,
                        readOnly = false,
                        placeholder = {
                            Text(text = stringResource(R.string.choose_currency), fontSize = 12.sp)
                        },
                        isError = state.isErrorBaseCurrency,
                        supportingText = {
                            if (state.isErrorBaseCurrency) {
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
                            if (state.isErrorBaseCurrency)
                                Icon(
                                    Icons.Default.Warning,
                                    "errorBaseCurrency",
                                    tint = MaterialTheme.colorScheme.error
                                )
                        },
                        suffix = {
                            Text(text = state.baseValute.code)
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                onEvent(
                                    RateEvent.OnChangeBaseValuteMenuVisibility(
                                        Color.White,
                                        true
                                    )
                                )
                            })
                            {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.List,
                                    contentDescription = "listBaseCurrencies"
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.White,
                            unfocusedContainerColor = state.focusColorBaseCurrency,
                            unfocusedTextColor = Color.White,
                    //        disabledContainerColor = state.focusColorBaseCurrency,
                            disabledTrailingIconColor = Color.Gray,
                        ),
                    )
                    AddBaseCurrency(
                        onEvent = onEvent,
                        state = state,
                    )
                }

// RESULT VALUTE
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = "На что меняем:",
                    color = Color.White
                )
                Row {
                    TextField(
                        //          value = state.resultValute.code,
                        value = state.textResultValute,
                        textStyle = TextStyle.Default.copy(
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        ),
                        onValueChange = { newText ->
                            onEvent(RateEvent.OnChangeTextResultValute(newText))
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        //     .padding(top = 20.dp),
                        enabled = true,
                        readOnly = false,
                        placeholder = {
                            Text(text = stringResource(R.string.choose_currency), fontSize = 12.sp)
                        },
                        isError = state.isErrorResultCurrency,
                        supportingText = {
                            if (state.isErrorResultCurrency) {
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
                            if (state.isErrorResultCurrency)
                                Icon(
                                    Icons.Default.Warning,
                                    "errorResultCurrency",
                                    tint = MaterialTheme.colorScheme.error
                                )
                        },
                        suffix = {
                            Text(text = state.resultValute.code)
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                onEvent(
                                    RateEvent.OnChangeResultValuteMenuVisibility(
                                        Color.White,
                                        true
                                    )
                                )
                            })
                            {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.List,
                                    contentDescription = "listResultCurrencies"
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.White,
                            unfocusedContainerColor = state.focusColorResultCurrency,
                            unfocusedTextColor = Color.White,
                     //       disabledContainerColor = state.focusColorResultCurrency,
                            disabledTrailingIconColor = Color.Gray,
                        ),
                    )
                    AddResultCurrency(
                        onEvent = onEvent,
                        state = state,
                    )
                }

                //       state.resultValute?.let{
                Text(
                    text = stringResource(
                        R.string.rate_text,
                        state.crossRate,
                        state.baseValute.code,
                        state.resultValute.nominal,
                        state.resultValute.name
                    ),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                //    }
//                if (state.resultCodeCurrency.isNotEmpty()) {
//                    Text(
//                        text = stringResource(
//                            R.string.rate_text,
//                            state.baseRate,
//                            state.resultCodeCurrency,
//                            state.nominal,
//                            state.title
//                        ),
//                        fontSize = 11.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White,
//                    )
//                }
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
                        onEvent(RateEvent.OnClickButtonChange)
                    }) {
                    Text(text = stringResource(R.string.change), fontSize = 18.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInputDataScreen() {
    InputDataScreen(state = RateState()) { }
}