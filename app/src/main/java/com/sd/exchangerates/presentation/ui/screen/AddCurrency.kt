package com.sd.exchangerates.presentation.ui.screen

import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.sd.exchangerates.domain.enums.Currency

@Composable
fun AddCurrency(
    expanded: MutableState<Boolean>,
    enumCurrency: MutableState<Currency>,
    color: MutableState<Color>,
    error: MutableState<Boolean>
) {
    DropdownMenu(
        modifier = Modifier.width(60.dp),
        expanded = expanded.value,
        offset = DpOffset((200).dp, (-10).dp),
        onDismissRequest = {
            expanded.value = false
            color.value = Color.Black
        },

        ) {
        DropdownMenuItem(text = {
            Text(Currency.USD.name, textAlign = TextAlign.Center)
        }, onClick = {
            enumCurrency.value = Currency.USD
            error.value = false
            expanded.value = false
            color.value = Color.Black
        })
        DropdownMenuItem(text = {
            Text(Currency.EUR.name, textAlign = TextAlign.Center)
        }, onClick = {
            enumCurrency.value = Currency.EUR
            error.value = false
            expanded.value = false
            color.value = Color.Black
        })
        DropdownMenuItem(text = {
            Text(Currency.GBP.name, textAlign = TextAlign.Center)
        }, onClick = {
            enumCurrency.value = Currency.GBP
            error.value = false
            expanded.value = false
            color.value = Color.Black
        })
        DropdownMenuItem(text = {
            Text(Currency.VND.name, textAlign = TextAlign.Center)
        }, onClick = {
            enumCurrency.value = Currency.VND
            error.value = false
            expanded.value = false
            color.value = Color.Black
        })
        DropdownMenuItem(text = {
            Text(Currency.TRY.name, textAlign = TextAlign.Center)
        }, onClick = {
            enumCurrency.value = Currency.TRY
            error.value = false
            expanded.value = false
            color.value = Color.Black
        })
        DropdownMenuItem(text = {
            Text(Currency.RSD.name, textAlign = TextAlign.Center)
        }, onClick = {
            enumCurrency.value = Currency.RSD
            error.value = false
            expanded.value = false
            color.value = Color.Black
        })
    }
}
