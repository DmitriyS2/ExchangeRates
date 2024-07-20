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

@Composable
fun AddCurrency(expanded: MutableState<Boolean>, currency: MutableState<String>, color:MutableState<Color>, error:MutableState<Boolean>) {
    DropdownMenu(
        modifier = Modifier.width(60.dp),
        expanded = expanded.value,
        offset = DpOffset((-100).dp, (-5).dp),
        onDismissRequest = {
            expanded.value = false
            color.value = Color.Black
        },

        ) {
        DropdownMenuItem(text = {
            Text(Currency.USD.name, textAlign = TextAlign.Center)
        }, onClick = {
            currency.value = Currency.USD.name
            error.value = false
            expanded.value = false
            color.value = Color.Black
        })
        DropdownMenuItem(text = {
            Text(Currency.EUR.name, textAlign = TextAlign.Center)
        }, onClick = {
            currency.value = Currency.EUR.name
            error.value = false
            expanded.value = false
            color.value = Color.Black
        })
        DropdownMenuItem(text = {
            Text(Currency.GBP.name, textAlign = TextAlign.Center)
        }, onClick = {
            currency.value = Currency.GBP.name
            error.value = false
            expanded.value = false
            color.value = Color.Black
        })
    }
}

enum class Currency {
    USD,
    EUR,
    GBP
}