package com.sd.exchangerates.presentation.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.exchangerates.domain.dto.Valute
import com.sd.exchangerates.domain.model.RateModel
import com.sd.exchangerates.domain.model.RateResponse
import com.sd.exchangerates.domain.usecase.GetResultSumCurrencyUseCase
import com.sd.exchangerates.util.BASE_STRING
import com.sd.exchangerates.util.separate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getResultSumCurrencyUseCase: GetResultSumCurrencyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RateModel())
    val state: StateFlow<RateModel> = _state.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val rateResponse: RateResponse? = getResultSumCurrencyUseCase()
            rateResponse?.let {
                _state.update {
                    it.copy(
                        loading = false,
                        error = false,
                        allCurrency = rateResponse.valutes,
                        date = rateResponse.date
                    )
                }
            } ?: _state.update { it.copy(loading = false, error = true) }
        }
    }

    fun onClickChange(): Boolean {
        return if (isErrorNumber(state.value.summaRub)) {
            _state.update { it.copy(isErrorSumma = true) }
            false
        } else if (state.value.currency == BASE_STRING) {
            _state.update { it.copy(isErrorSumma = false, isErrorCurrency = true) }
            false
        } else {
            _state.update { it.copy(isErrorSumma = false, isErrorCurrency = false, result = getOnlyTwoSignsInResult()) }
            true
        }
    }

    fun setSummaRub(value: String) {
        _state.update { it.copy(summaRub = value) }
    }

    private fun getOnlyTwoSignsInResult(): String =
        (((((state.value.summaRub.toLong() * state.value.nominal) / state.value.rate) * 100).toLong()) / 100.0).separate()

    fun setValute(valute: Valute) {
        _state.update {
            it.copy(
                currency = valute.code,
                rate = valute.value,
                title = valute.name,
                nominal = valute.nominal,
                isErrorCurrency = false,
                isValuteMenuVisible = false,
            )
        }
    }

    fun setFocusColorCurrency(color: Color) {
        _state.update { it.copy(focusColorCurrency = color) }
    }

    fun setValuteMenuVisibility(flag: Boolean) {
        _state.update { it.copy(isValuteMenuVisible = flag) }
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
}