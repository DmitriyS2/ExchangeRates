package com.sd.exchangerates.domain.usecase

import com.sd.exchangerates.domain.model.RateResponse
import com.sd.exchangerates.domain.model.toRateResponse
import com.sd.exchangerates.domain.repository.DataStorageRepository
import com.sd.exchangerates.domain.repository.DatabaseRepository
import com.sd.exchangerates.domain.repository.NetworkRepository
import com.sd.exchangerates.util.toDate
import com.sd.exchangerates.util.toFormattedString
import com.sd.exchangerates.util.toStringAsLocalDate
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class GetAllCurrenciesUseCaseImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
    private val dataStorageRepository: DataStorageRepository,
) : GetAllCurrenciesUseCase {

    override suspend fun invoke(): RateResponse? {
        val valuteFromDB = databaseRepository.getRateFromDB()
        val createRequest = dataStorageRepository.createRequestToBE.first()
        val currentDate = LocalDate.now()
        return when {
            createRequest.isEmpty() -> updateValuteInDb()

            createRequest.toStringAsLocalDate().toDate() == currentDate -> {
                valuteFromDB?.toRateResponse() ?: updateValuteInDb()
            }

            createRequest.toStringAsLocalDate().toDate() < currentDate -> updateValuteInDb()

            else -> null
        }
    }

    private suspend fun updateValuteInDb(): RateResponse? {
        val valuteFromNetwork = networkRepository.getCurrenciesFromBE()
        valuteFromNetwork?.let { databaseRepository.insertValutes(valuteFromNetwork) }
        return valuteFromNetwork.toRateResponse()
    }
}