package com.sd.exchangerates.domain.repository

import com.sd.exchangerates.domain.dto.Rate

interface Repository {
    suspend fun getResultSumCurrency(): Rate?
}