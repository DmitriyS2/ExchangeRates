package com.sd.exchangerates.presentation.repository

import com.sd.exchangerates.domain.dto.Rate

interface Repository {
    suspend fun getResultSumCurrency(): Rate?
}