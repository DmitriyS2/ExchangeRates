package com.sd.exchangerates.data

import com.sd.exchangerates.domain.dto.Rate
import com.sd.exchangerates.domain.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : Repository {
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