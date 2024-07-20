package com.sd.exchangerates.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.exchangerates.domain.dto.Rate
import com.sd.exchangerates.domain.model.RateModel
import com.sd.exchangerates.domain.usecase.GetResultSumCurrencyUseCase
import com.sd.exchangerates.presentation.ui.screen.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getResultSumCurrencyUseCase: GetResultSumCurrencyUseCase
) : ViewModel() {

    private val _summa: MutableLiveData<Long> = MutableLiveData(0L)
    val summa: LiveData<Long>
        get() = _summa

    private val _currency: MutableLiveData<String> = MutableLiveData("")
    val currency: LiveData<String>
        get() = _currency

    private val _rate: MutableLiveData<Double> = MutableLiveData()
    val rate: LiveData<Double>
        get() = _rate

    private val _result: MutableLiveData<RateModel> = MutableLiveData()
    val result: LiveData<RateModel>
        get() = _result

    //  val baseCurrency = "RUB"

    fun getResult() {
        viewModelScope.launch {
            try {
                _result.value = RateModel(loading = true)
                val currentRate: Rate = getResultSumCurrencyUseCase() ?: Rate()
                Log.d("MyLog", "rate=$currentRate")
                if (currentRate == Rate()) {
                    _result.value = RateModel(error = true)
                    return@launch
                } else {
                    _result.value = RateModel(
                        summaRub = _summa.value ?: 0L,
                        baseCurrent = currentRate.base,
                        result = when (_currency.value) {
                            Currency.USD.toString() -> {
                                currentRate.rates.USD*(_summa.value ?: 0L)
                            }

                            Currency.EUR.toString() -> {
                                currentRate.rates.EUR*(_summa.value ?: 0L)
                            }

                            Currency.GBP.toString() -> {
                                currentRate.rates.GBP*(_summa.value ?: 0L)
                            }

                            else -> 0.0
                        },
                        currency = _currency.value ?: ""
                    )
                }
            } catch (e: Exception) {
                _result.value = RateModel(error = true)
            }
        }
    }

    fun setCurrency(value: String) {
        _currency.value = value
    }

    fun setSum(value: String) {
        _summa.value = value.toLong()
    }
}