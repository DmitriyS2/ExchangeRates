package com.sd.exchangerates.domain.usecase

import com.sd.exchangerates.domain.dto.Rate

interface GetResultSumCurrencyUseCase {

    suspend operator fun invoke(): Rate?
}