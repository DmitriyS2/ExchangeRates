package com.sd.exchangerates.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sd.exchangerates.R
import com.sd.exchangerates.domain.dto.Valute
import com.sd.exchangerates.presentation.ui.contract.RateEvent
import com.sd.exchangerates.presentation.ui.contract.RateState
import com.sd.exchangerates.presentation.ui.theme.ExchangeRatesTheme
import com.sd.exchangerates.util.noActions
import com.sd.exchangerates.util.separate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    state: RateState,
    onEvent: (RateEvent) -> Unit,
) {

    TopAppBar(
        title = {
            Text(text = stringResource(R.string.result))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            scrolledContainerColor = Color.Black,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            IconButton(onClick = {
                onEvent(RateEvent.OnClickBackFromResult)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "backToInputData"
                )
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        Alignment.Center
    ) {
        state.let {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Yellow),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${it.baseAmount.separate()} ${it.baseValute.code}",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                    Text(text = "=", fontSize = 24.sp)
                    Text(
                        text = "${it.resultAmount} ${it.resultValute.code}",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                    Text(
                        text = stringResource(
                            R.string.rate_on_date,
                            it.date,
                            it.crossRate,
                            it.baseValute.code,
                            it.resultValute.nominal,
                            it.resultValute.name
                        ),
                        fontSize = 8.sp,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResultScreen() {
    ExchangeRatesTheme {
        ResultScreen(
            RateState(baseAmount = "1000", resultAmount = "10", resultValute = Valute(code = "USD"))
        ) { noActions() }
    }
}
