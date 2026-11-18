package com.sd.exchangerates.data.network

import com.sd.exchangerates.domain.dto.Rate
import com.sd.exchangerates.domain.dto.rub
import com.sd.exchangerates.domain.repository.DataStorageRepository
import com.sd.exchangerates.domain.repository.NetworkRepository
import com.sd.exchangerates.util.toStringAsLocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dataStorageRepository: DataStorageRepository,
) : NetworkRepository {

    override suspend fun getResultSumCurrency(): Rate? {
        try {
            val response = apiService.getCompleteRate()
            if (!response.isSuccessful) {
                return null
            }
            val result = response.body()
            return result?.let {
                val valute = result.valute.toMutableMap()
                valute[rub.code] = rub
                dataStorageRepository.setDateResponseFromBE(it.date.toStringAsLocalDate())
                Rate(
                 //   date = it.date,
                    valute = valute
                )
            }
        } catch (_: Exception) {
            return null
        }
    }
}