package com.sd.exchangerates.domain.usecase

import com.sd.exchangerates.domain.model.RateResponse
import com.sd.exchangerates.domain.model.toRateResponse
import com.sd.exchangerates.domain.repository.DatabaseRepository
import com.sd.exchangerates.domain.repository.NetworkRepository
import com.sd.exchangerates.util.toDate
import com.sd.exchangerates.util.toStringAsLocalDate
import java.time.LocalDate
import javax.inject.Inject

class GetResultSumCurrencyUseCaseImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : GetResultSumCurrencyUseCase {

    override suspend fun invoke(): RateResponse? {
        val valuteFromDB = databaseRepository.getRateFromDB()
        val currentDate = LocalDate.now()
        return valuteFromDB?.let {
            if (it.date.toStringAsLocalDate().toDate() < currentDate) {
                updateValuteInDb()
            } else {
                it.toRateResponse()
            }
        } ?: updateValuteInDb()
    }

    private suspend fun updateValuteInDb(): RateResponse? {
        val valuteFromNetwork = networkRepository.getResultSumCurrency()
        valuteFromNetwork?.let { databaseRepository.insertValutes(valuteFromNetwork) }
        return valuteFromNetwork.toRateResponse()
    }
}