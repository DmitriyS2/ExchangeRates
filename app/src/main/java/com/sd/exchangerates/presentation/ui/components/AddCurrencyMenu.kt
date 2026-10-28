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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.sd.exchangerates.domain.dto.Valute
import com.sd.exchangerates.presentation.ui.contract.RateEvent
import com.sd.exchangerates.presentation.ui.contract.RateState

@Composable
fun AddCurrency(
    onEvent: (RateEvent) -> Unit,
    state: RateState,
) {
    DropdownMenu(
        modifier = Modifier
            .width(60.dp)
            .heightIn(max = 200.dp),
        expanded = state.isValuteMenuVisible,
        offset = DpOffset((200).dp, (-10).dp),
        onDismissRequest = {
            onEvent.invoke(RateEvent.OnChangeValuteMenuVisibility(Color.Black, false))
        },
    ) {
        state.allCurrency.forEach { valute ->
            DropdownMenuItem(text = {
                Text(valute.code, textAlign = TextAlign.Center)
            }, onClick = {
                onEvent.invoke(RateEvent.OnChangeValute(valute, Color.Black))
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddCurrencyMenu() {
    Box(modifier = Modifier.size(300.dp)) {
        Box(modifier = Modifier.size(1.dp)) {
            AddCurrency(
                {},
                RateState(
                    isValuteMenuVisible = true,
                    allCurrency = listOf(Valute(code = "RUB"), Valute(code = "USD"))
                )
            )
        }
    }
}
