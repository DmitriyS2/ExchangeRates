package com.sd.exchangerates.domain.usecase

import com.sd.exchangerates.domain.model.RateResponse
import com.sd.exchangerates.domain.model.toRateResponse
import com.sd.exchangerates.domain.repository.Repository
import javax.inject.Inject

class GetResultSumCurrencyUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetResultSumCurrencyUseCase {

    override suspend fun invoke(): RateResponse? {
        return repository.getResultSumCurrency().toRateResponse()
    }
}