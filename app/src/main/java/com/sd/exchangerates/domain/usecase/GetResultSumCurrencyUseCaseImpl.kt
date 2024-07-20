package com.sd.exchangerates.domain.usecase

import com.sd.exchangerates.domain.dto.Rate
import com.sd.exchangerates.presentation.repository.Repository
import javax.inject.Inject

class GetResultSumCurrencyUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetResultSumCurrencyUseCase {

    override suspend fun invoke(): Rate? = repository.getResultSumCurrency()
}