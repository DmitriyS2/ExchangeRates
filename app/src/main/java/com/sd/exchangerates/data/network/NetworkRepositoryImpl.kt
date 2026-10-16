package com.sd.exchangerates.data.network

import com.sd.exchangerates.domain.dto.Rate
import com.sd.exchangerates.domain.repository.NetworkRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : NetworkRepository {

    override suspend fun getResultSumCurrency(): Rate? {
        try {
            val response = apiService.getCompleteRate()
            if (!response.isSuccessful) {
                return null
            }
            return response.body()
        } catch (_: Exception) {
            return null
        }
    }
}