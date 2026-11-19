package com.sd.exchangerates.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.exchangerates.domain.dto.Valute
import com.sd.exchangerates.domain.model.RateResponse
import com.sd.exchangerates.domain.repository.DataStorageRepository
import com.sd.exchangerates.domain.usecase.GetAllCurrenciesUseCase
import com.sd.exchangerates.presentation.ui.contract.RateEffect
import com.sd.exchangerates.presentation.ui.contract.RateEvent
import com.sd.exchangerates.presentation.ui.contract.RateState
import com.sd.exchangerates.util.EMPTY
import com.sd.exchangerates.util.separate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val dataStorageRepository: DataStorageRepository,
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

            is RateEvent.OnChangeResultValuteMenuVisibility -> changeResultMenuVisibility(event.isVisible)
            is RateEvent.OnChangeResultValute -> setResultValute(event.valute)

            is RateEvent.OnChangeBaseValuteMenuVisibility -> changeBaseMenuVisibility(event.isVisible)
            is RateEvent.OnChangeBaseValute -> setBaseValute(event.valute)

            RateEvent.OnClickButtonChange -> onClickChange()

            RateEvent.OnClickBackFromResult -> navigateToInputData()

            is RateEvent.OnChangeTextBaseValute -> setTextBaseValute(event.newText)
            is RateEvent.OnChangeTextResultValute -> setTextResultValute(event.newText)

            RateEvent.OnClickSwap -> swap()

            RateEvent.OnClickDoneKeyboard -> checkAmount()
        }
    }

    private fun getData() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val rateResponse: RateResponse? = getAllCurrenciesUseCase()
            val date = dataStorageRepository.dateResponseFromBE.first()
            val dateRequest = dataStorageRepository.createRequestToBE.first()
            Log.d("MyLog", "dateResp=$date")
            Log.d("MyLog", "dateRequest=$dateRequest")

            rateResponse?.let {
                _state.update {
                    it.copy(
                        loading = false,
                        error = false,
                        allCurrency = rateResponse.valutes,
                        allBaseCurrency = rateResponse.valutes,
                        allResultCurrency = rateResponse.valutes,
                        date = date
                    )
                }
            } ?: _state.update { it.copy(loading = false, error = true) }
        }
    }

    private fun checkAmount() {
        _state.update { it.copy(isErrorAmount = isErrorNumber(_state.value.baseAmount)) }
    }

    private fun swap() {
        val base = _state.value.baseValute
        val result = _state.value.resultValute
        val crossRate = (result.nominal * base.value) / result.value
        _state.update { it.copy(baseValute = result, resultValute = base, crossRate = crossRate) }
    }

    private fun setTextBaseValute(text: String) {
        val baseValutes: List<Valute> = _state.value.allCurrency.filter {
            it.code.lowercase().contains(text.lowercase()) || it.name.lowercase()
                .contains(text.lowercase())
        }
        _state.update {
            it.copy(
                allBaseCurrency = baseValutes,
                textBaseValute = text,
                isBaseValuteMenuVisible = true,
            )
        }
    }

    private fun setTextResultValute(text: String) {
        val resultValutes: List<Valute> = _state.value.allCurrency.filter {
            it.code.lowercase().contains(text.lowercase()) || it.name.lowercase()
                .contains(text.lowercase())
        }
        _state.update {
            it.copy(
                allResultCurrency = resultValutes,
                textResultValute = text,
                isResultValuteMenuVisible = true,
            )
        }
    }

    private fun onClickChange() {
        if (isErrorNumber(_state.value.baseAmount)) {
            _state.update { it.copy(isErrorAmount = true) }
        } else {
            _state.update {
                it.copy(
                    isErrorAmount = false,
                    resultAmount = calculateResultAmount()
                )
            }
            navigateToResult()
        }
    }

    private fun setBaseAmount(value: String) {
        _state.update { it.copy(baseAmount = value) }
    }

    private fun setResultValute(valute: Valute) {
        val crossRate =
            (_state.value.baseValute.nominal * valute.value) / _state.value.baseValute.value

        _state.update {
            it.copy(
                resultValute = valute,
                crossRate = crossRate,
                isResultValuteMenuVisible = false,
            )
        }
    }

    private fun setBaseValute(valute: Valute) {
        val crossRate = (valute.nominal * _state.value.resultValute.value) / valute.value

        _state.update {
            it.copy(
                baseValute = valute,
                crossRate = crossRate,
                isBaseValuteMenuVisible = false,
            )
        }
    }

    private fun calculateResultAmount(
        baseValute: Valute = _state.value.baseValute,
        resultValute: Valute = _state.value.resultValute,
        baseAmount: String = _state.value.baseAmount
    ): String {
        return try {
            ((baseAmount.toInt() * resultValute.nominal * baseValute.value) / (resultValute.value * baseValute.nominal)).separate()
        } catch (_: Exception) {
            EMPTY
        }
    }

    private fun changeBaseMenuVisibility(isVisible: Boolean) {
        _state.update { it.copy(isBaseValuteMenuVisible = isVisible) }
    }

    private fun changeResultMenuVisibility(isVisible: Boolean) {
        _state.update { it.copy(isResultValuteMenuVisible = isVisible) }
    }

    private fun isErrorNumber(text: String): Boolean {
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