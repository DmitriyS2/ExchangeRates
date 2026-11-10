package com.sd.exchangerates.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.sd.exchangerates.domain.dto.Valute
import com.sd.exchangerates.presentation.ui.contract.RateEvent
import com.sd.exchangerates.presentation.ui.contract.RateState
import com.sd.exchangerates.util.noActions

@Composable
fun AddResultCurrency(
    onEvent: (RateEvent) -> Unit,
    state: RateState,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager?,
) {
    if (state.allResultCurrency.isNotEmpty()) {
        DropdownMenu(
            modifier = Modifier
                .width(60.dp)
                .heightIn(max = 200.dp),
            expanded = state.isResultValuteMenuVisible,
            offset = DpOffset((200).dp, (-10).dp),
            properties = PopupProperties(focusable = false),
            onDismissRequest = {
                onEvent.invoke(RateEvent.OnChangeResultValuteMenuVisibility(Color.Black, false))
            },
        ) {
            state.allResultCurrency.forEach { valute ->
                DropdownMenuItem(text = {
                    Text(valute.code, textAlign = TextAlign.Center)
                }, onClick = {
                    onEvent.invoke(RateEvent.OnChangeResultValute(valute, Color.Black))
                    keyboardController?.hide()
                    focusManager?.clearFocus()
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddResultCurrencyMenu() {
    Box(modifier = Modifier.size(300.dp)) {
        Box(modifier = Modifier.size(1.dp)) {
            AddResultCurrency(
                { noActions() },
                RateState(
                    isResultValuteMenuVisible = true,
                    allCurrency = listOf(Valute(code = "RUB"), Valute(code = "USD"))
                ),
                null,
                null,
            )
        }
    }
}