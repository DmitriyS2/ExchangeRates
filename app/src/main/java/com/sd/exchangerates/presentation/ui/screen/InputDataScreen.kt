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
import com.sd.exchangerates.R
import com.sd.exchangerates.presentation.ui.components.AddCurrency
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
                TextField(
                    value = state.summaRub,
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
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                    placeholder = {
                        Text(text = stringResource(R.string.enter_sum), fontSize = 12.sp)
                    },
                    isError = state.isErrorSumma,
                    supportingText = {
                        if (state.isErrorSumma) {
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
                        if (state.isErrorSumma)
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
                        value = state.currency,
                        textStyle = TextStyle.Default.copy(
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        ),
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        enabled = false,
                        readOnly = false,
                        placeholder = {
                            Text(text = stringResource(R.string.choose_currency), fontSize = 12.sp)
                        },
                        isError = state.isErrorCurrency,
                        supportingText = {
                            if (state.isErrorCurrency) {
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
                            if (state.isErrorCurrency)
                                Icon(
                                    Icons.Default.Warning,
                                    "errorCurrency",
                                    tint = MaterialTheme.colorScheme.error
                                )
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                onEvent(RateEvent.OnChangeValuteMenuVisibility(Color.White, true))
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
                            disabledContainerColor = state.focusColorCurrency,
                            disabledTrailingIconColor = Color.Gray,
                        ),
                    )
                    AddCurrency(
                        onEvent = onEvent,
                        state = state,
                    )
                }
                if (state.currency.isNotEmpty()) {
                    Text(
                        text = stringResource(
                            R.string.rate_text,
                            state.rate,
                            state.baseCurrent,
                            state.nominal,
                            state.title
                        ),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
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