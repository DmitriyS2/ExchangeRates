package com.sd.exchangerates.presentation.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sd.exchangerates.R
import com.sd.exchangerates.presentation.ui.components.AddBaseCurrency
import com.sd.exchangerates.presentation.ui.components.AddResultCurrency
import com.sd.exchangerates.presentation.ui.contract.RateEvent
import com.sd.exchangerates.presentation.ui.contract.RateState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDataScreen(
    state: RateState,
    onEvent: (RateEvent) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    TopAppBar(
        title = {
            Text(text = stringResource(R.string.input_sum), fontSize = 24.sp)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.LightGray,
            scrolledContainerColor = Color.White,
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
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = stringResource(R.string.from),
                    color = Color.White
                )
                OutlinedTextField(
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
                        onEvent(RateEvent.OnClickDoneKeyboard)
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
                        Text(
                            modifier = Modifier.background(Color.Transparent),
                            text = stringResource(R.string.amount),
                            color = Color.Gray
                        )
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
                        unfocusedTextColor = Color.Black,
                   //     focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        errorCursorColor = Color.Red,
                        focusedLabelColor = Color.Red,
                        unfocusedLabelColor = Color.Red
                    ),
                )

// BASE VALUTE
                Row {
                    OutlinedTextField(
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
                        enabled = true,
                        readOnly = false,
                        placeholder = {
                            Text(text = stringResource(R.string.choose_currency), fontSize = 12.sp)
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }),
                        label = {
                            Text(text = stringResource(R.string.currency), color = Color.Gray)
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                onEvent(RateEvent.OnChangeBaseValuteMenuVisibility(true))
                            })
                            {
                                Text(text = state.baseValute.code)
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.White,
                            unfocusedTextColor = Color.LightGray,
                            unfocusedContainerColor = Color.LightGray,
                            disabledTrailingIconColor = Color.Gray,
                        ),
                    )
                    AddBaseCurrency(
                        onEvent = onEvent,
                        state = state,
                    )
                }

                IconButton(
                    modifier = Modifier.padding(top = 8.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Yellow,
                        contentColor = Color.Gray
                    ),
                    onClick = {
                        onEvent(RateEvent.OnClickSwap)
                    }) {
                    Icon(
                        imageVector = Icons.Default.SwapVert,
                        contentDescription = "swap"
                    )
                }

// RESULT VALUTE
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    textAlign = TextAlign.End,
                    text = stringResource(R.string.to),
                    color = Color.White
                )
                Row {
                    OutlinedTextField(
                        value = state.textResultValute,
                        textStyle = TextStyle.Default.copy(
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        ),
                        onValueChange = { newText ->
                            onEvent(RateEvent.OnChangeTextResultValute(newText))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        readOnly = false,
                        placeholder = {
                            Text(text = stringResource(R.string.choose_currency), fontSize = 12.sp)
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }),
                        label = {
                            Text(text = stringResource(R.string.currency), color = Color.Gray)
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                onEvent(RateEvent.OnChangeResultValuteMenuVisibility(true))
                            })
                            {
                                Text(text = state.resultValute.code)
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.White,
                            unfocusedTextColor = Color.LightGray,
                            unfocusedContainerColor = Color.LightGray,
                            disabledTrailingIconColor = Color.Gray,
                        ),
                    )
                    AddResultCurrency(
                        onEvent = onEvent,
                        state = state,
                    )
                }

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