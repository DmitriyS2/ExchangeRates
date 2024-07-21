package com.sd.exchangerates.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.exchangerates.domain.dto.Rate
import com.sd.exchangerates.domain.enums.Currency
import com.sd.exchangerates.domain.model.RateModel
import com.sd.exchangerates.domain.usecase.GetResultSumCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getResultSumCurrencyUseCase: GetResultSumCurrencyUseCase
) : ViewModel() {

    private var summa: Long = 0L

    private var currentCurrency: Currency = Currency.NONE

    private val _result: MutableLiveData<RateModel> = MutableLiveData()
    val result: LiveData<RateModel>
        get() = _result

    fun getResult() {
        viewModelScope.launch {
            try {
                _result.value = RateModel(loading = true)
                val currentRate: Rate = getResultSumCurrencyUseCase() ?: Rate()
                if (currentRate == Rate()) {
                    _result.value = RateModel(error = true)
                } else {
                    _result.value = RateModel(
                        summaRub = summa.separate(),
                        baseCurrent = currentRate.base,
                        result = when (currentCurrency.name) {
                            Currency.USD.toString() -> {
                                getOnlyTwoSignsInResult(currentRate.rates.USD)
                            }

                            Currency.EUR.toString() -> {
                                getOnlyTwoSignsInResult(currentRate.rates.EUR)
                            }

                            Currency.GBP.toString() -> {
                                getOnlyTwoSignsInResult(currentRate.rates.GBP)
                            }

                            Currency.VND.toString() -> {
                                getOnlyTwoSignsInResult(currentRate.rates.VND)
                            }

                            Currency.TRY.toString() -> {
                                getOnlyTwoSignsInResult(currentRate.rates.TRY)
                            }

                            Currency.RSD.toString() -> {
                                getOnlyTwoSignsInResult(currentRate.rates.RSD)
                            }

                            else -> 0.0
                        }.toString(),
                        currency = currentCurrency.name,
                        date = currentRate.date.split("-").reversed().joinToString("-"),
                        rate = when (currentCurrency.name) {
                            Currency.USD.toString() -> getOnlyTwoSignsInRate(currentRate.rates.USD)
                            Currency.EUR.toString() -> getOnlyTwoSignsInRate(currentRate.rates.EUR)
                            Currency.GBP.toString() -> getOnlyTwoSignsInRate(currentRate.rates.GBP)
                            Currency.VND.toString() -> getOnlyTwoSignsInRate(currentRate.rates.VND)
                            Currency.TRY.toString() -> getOnlyTwoSignsInRate(currentRate.rates.TRY)
                            Currency.RSD.toString() -> getOnlyTwoSignsInRate(currentRate.rates.RSD)
                            else -> 0.0
                        },
                        title = currentCurrency.title,
                        nominal = currentCurrency.nominal
                    )
                }
            } catch (e: Exception) {
                _result.value = RateModel(error = true)
            }
        }
    }

    fun setCurrency(value: Currency) {
        currentCurrency = value
    }

    fun setSum(value: String) {
        summa = value.toLong()
    }

    private fun getOnlyTwoSignsInResult(rate: Double): String =
        ((((summa * rate) * 100).toLong()) / 100.0).separate()

    private fun getOnlyTwoSignsInRate(rate: Double): Double =
        ((currentCurrency.nominal / rate * 100).toLong()) / 100.0

    private fun Long.separate(): String = DecimalFormat().format(this).toString()
    private fun Double.separate(): String = DecimalFormat().format(this).toString()
}