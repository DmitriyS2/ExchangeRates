package com.sd.exchangerates.domain.repository

import com.sd.exchangerates.domain.dto.Rate

interface NetworkRepository {
    suspend fun getResultSumCurrency(): Rate?
}