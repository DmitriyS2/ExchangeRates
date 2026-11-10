package com.sd.exchangerates.presentation.viewmodel

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.exchangerates.domain.dto.Valute
import com.sd.exchangerates.domain.model.RateResponse
import com.sd.exchangerates.domain.usecase.GetResultSumCurrencyUseCase
import com.sd.exchangerates.presentation.ui.contract.RateEffect
import com.sd.exchangerates.presentation.ui.contract.RateEvent
import com.sd.exchangerates.presentation.ui.contract.RateState
import com.sd.exchangerates.util.EMPTY
import com.sd.exchangerates.util.separate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getResultSumCurrencyUseCase: GetResultSumCurrencyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RateState())
    val state: StateFlow<RateState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<RateEffect>()
    val effect: SharedFlow<RateEffect> = _effect.asSharedFlow()

    init {
        getData()
    }

    fun handleEvent(event: RateEvent) {
        when (event) {
            RateEvent.OnRetryClick -> getData()
            is RateEvent.OnChangeSumma -> setBaseAmount(event.newSumma)
            is RateEvent.OnChangeResultValuteMenuVisibility -> changeResultMenuVisibility(
                event.color,
                event.isVisible
            )

            is RateEvent.OnChangeResultValute -> setResultValute(event.valute, event.color)

            is RateEvent.OnChangeBaseValuteMenuVisibility -> changeBaseMenuVisibility(
                event.color,
                event.isVisible
            )

            is RateEvent.OnChangeBaseValute -> setBaseValute(event.valute, event.color)
            RateEvent.OnClickButtonChange -> onClickChange()
            RateEvent.OnClickBackFromResult -> navigateToInputData()

            is RateEvent.OnChangeTextBaseValute -> setTextBaseValute(event.newText)
            is RateEvent.OnChangeTextResultValute -> setTextResultValute(event.newText)
        }
    }

    private fun getData() {
        viewModelScope.launch() {
            _state.update { it.copy(loading = true) }
            val rateResponse: RateResponse? = getResultSumCurrencyUseCase()
            rateResponse?.let {
                _state.update {
                    it.copy(
                        loading = false,
                        error = false,
                        allCurrency = rateResponse.valutes,
                        allBaseCurrency = rateResponse.valutes,
                        allResultCurrency = rateResponse.valutes,
                        date = rateResponse.date
                    )
                }
            } ?: _state.update { it.copy(loading = false, error = true) }
        }
    }

    private fun setTextBaseValute(text: String) {
        val baseValutes: List<Valute> = _state.value.allCurrency.filter {
            it.code.lowercase().contains(text.lowercase()) || it.name.lowercase().contains(text.lowercase())
        }
        _state.update { it.copy(allBaseCurrency = baseValutes, textBaseValute = text, isBaseValuteMenuVisible = true, focusColorBaseCurrency = Color.White) }
    }

    private fun setTextResultValute(text: String) {
        val resultValutes: List<Valute> = _state.value.allCurrency.filter {
            it.code.lowercase().contains(text.lowercase()) || it.name.lowercase().contains(text.lowercase())
        }
        _state.update { it.copy(allResultCurrency = resultValutes, textResultValute = text, isResultValuteMenuVisible = true, focusColorResultCurrency = Color.White) }
    }

    private fun onClickChange() {
        //    val isErrorBaseAmount = isErrorNumber(state.value.baseAmount)
        //   val isErrorBaseCurrency = state.value.baseValute.code == BASE_STRING
        //    val isErrorResultCurrency = state.value.resultValute?.code == BASE_STRING

        if (isErrorNumber(state.value.baseAmount)) {
            _state.update { it.copy(isErrorAmount = true) }
        } else {
            _state.update {
                it.copy(
                    isErrorAmount = false,
                    //       isErrorBaseCurrency = isErrorBaseCurrency,
                    //       isErrorResultCurrency = isErrorResultCurrency,
                    //   resultAmount = getOnlyTwoSignsInResult() // TODO можно здесь считать
                )
            }
            navigateToResult()
        }
    }

    private fun setBaseAmount(value: String) {
        val resultAmount = calculateResultAmount(baseAmount = value)
        _state.update { it.copy(baseAmount = value, resultAmount = resultAmount) }
    }

    fun separateAmount() {
        _state.update { it.copy(baseAmount = it.baseAmount.separate()) }
    }
//
//    private fun getOnlyTwoSignsInResult(): String {
//        return try {
//            (((((state.value.baseAmount.toLong() * state.value.resultValute.nominal) / state.value.baseValute.value) * 100).toLong()) / 100.0).separate()
//        } catch (_: Exception) {
//            EMPTY
//        }
//    }

    private fun setResultValute(valute: Valute, color: Color) {
        //       val crossRate = (_state.value.baseValute.value * _state.value.baseValute.nominal) / valute.value
        val crossRate = (_state.value.baseValute.nominal * valute.value) / _state.value.baseValute.value
        val resultAmount = calculateResultAmount(resultValute = valute)

        _state.update {
            it.copy(
                resultValute = valute,
                crossRate = crossRate,
                resultAmount = resultAmount,
                //       resultCodeCurrency = valute.code,
                //      resultRate = valute.value,
                //      title = valute.name,
                //      nominal = valute.nominal,
                //    isErrorResultCurrency = false,
                isResultValuteMenuVisible = false,
                focusColorResultCurrency = color,
            )
        }
    }

    private fun setBaseValute(valute: Valute, color: Color) {
        //    val crossRate = (valute.value * valute.nominal) / _state.value.resultValute.value
        val crossRate = (valute.nominal * _state.value.resultValute.value) / valute.value
        val resultAmount = calculateResultAmount(baseValute = valute)

        _state.update {
            it.copy(
                baseValute = valute,
                crossRate = crossRate,
                resultAmount = resultAmount,
                //      baseCodeCurrency = valute.code,
                //      baseRate = valute.value,
                //      title = valute.name,
                //       nominal = valute.nominal,
                //    isErrorBaseCurrency = false,
                isBaseValuteMenuVisible = false,
                focusColorBaseCurrency = color,
            )
        }
    }

    private fun calculateCrossRate(): Double {
        return (_state.value.baseValute.value * _state.value.baseValute.nominal) / _state.value.resultValute.value
    }

    private fun calculateResultAmount(
        baseValute: Valute = _state.value.baseValute,
        resultValute: Valute = _state.value.resultValute,
        baseAmount: String = _state.value.baseAmount
    ): String {
        return try {
            //    with(_state.value) {
            Log.d(
                "MyLog",
                "baseAmount=${baseAmount.toInt()}, resNominal=${resultValute.nominal}, resRate=${resultValute.value}, baseNom=${baseValute.nominal}, baseRate=${baseValute.value}"
            )
            Log.d("MyLog", "1=${baseAmount.toInt() * resultValute.nominal * baseValute.value}")
            Log.d("MyLog", "2=${resultValute.value * baseValute.nominal}")
            Log.d(
                "MyLog",
                "3=${(baseAmount.toInt() * resultValute.nominal * baseValute.value) / (resultValute.value * baseValute.nominal)}"
            )
            Log.d(
                "MyLog",
                "4=${((baseAmount.toInt() * resultValute.nominal * baseValute.value) / (resultValute.value * baseValute.nominal)).separate()}"
            )
            ((baseAmount.toInt() * resultValute.nominal * baseValute.value) / (resultValute.value * baseValute.nominal)).separate()
            //     }
        } catch (_: Exception) {
            EMPTY
        }
    }

    private fun calculateResultAmount(crossRate: Double): String {
        Log.d(
            "MyLog",
            "baseAmount=${_state.value.baseAmount}, ResNominal=${_state.value.resultValute.nominal}, crossRate=${crossRate}"
        )
        return try {
            val res =
                ((_state.value.baseAmount.toInt() * _state.value.resultValute.nominal) / crossRate).separate()
            Log.d("MyLog", "res1=$res")
            res
        } catch (_: Exception) {
            Log.d("MyLog", "res2=")
            EMPTY
        }
    }

    private fun changeBaseMenuVisibility(color: Color, isVisible: Boolean) {
        _state.update {
            it.copy(
                focusColorBaseCurrency = color,
                isBaseValuteMenuVisible = isVisible
            )
        }
    }

    private fun changeResultMenuVisibility(color: Color, isVisible: Boolean) {
        _state.update {
            it.copy(
                focusColorResultCurrency = color,
                isResultValuteMenuVisible = isVisible
            )
        }
    }

    fun isErrorNumber(text: String): Boolean {
        var flag = false
        if (text.isEmpty()) {
            flag = true
        } else {
            try {
                val number: Long = text.toLong()
                if (number <= 0) {
                    flag = true
                }
            } catch (_: Exception) {
                flag = true
            }
        }
        return flag
    }

    private fun navigateToResult() {
        viewModelScope.launch {
            _effect.emit(RateEffect.NavigateToResult)
        }
    }

    private fun navigateToInputData() {
        viewModelScope.launch {
            _effect.emit(RateEffect.NavigateToInputData)
        }
    }
}