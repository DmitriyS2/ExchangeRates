package com.sd.exchangerates.presentation.ui.screen

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.sd.exchangerates.domain.model.RateModel
import com.sd.exchangerates.presentation.viewmodel.MainViewModel

@Composable
fun AddCurrency(
    vm: MainViewModel,
    state: State<RateModel>,
) {
    DropdownMenu(
        modifier = Modifier
            .width(60.dp)
            .heightIn(max = 200.dp),
        expanded = state.value.isValuteMenuVisible,
        offset = DpOffset((200).dp, (-10).dp),
        onDismissRequest = {
            vm.setValuteMenuVisibility(false)
            vm.setFocusColorCurrency(Color.Black)
        },

        ) {
        state.value.allCurrency.forEach { valute ->
            DropdownMenuItem(text = {
                Text(valute.code, textAlign = TextAlign.Center)
            }, onClick = {
                vm.setValute(valute)
                vm.setFocusColorCurrency(Color.Black)
            })
        }
    }
}
