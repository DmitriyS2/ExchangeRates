package com.sd.exchangerates.data

import com.sd.exchangerates.domain.dto.Rate
import com.sd.exchangerates.presentation.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
): Repository {
    override suspend fun getResultSumCurrency(): Rate? {
        try {
            val response = apiService.getRate()
            if(!response.isSuccessful) {
                return null
            }
            return response.body()
        } catch (e:Exception) {
            return null
        }

    }
}