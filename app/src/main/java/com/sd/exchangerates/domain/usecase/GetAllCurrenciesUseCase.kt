package com.sd.exchangerates.domain.usecase

import com.sd.exchangerates.domain.model.RateResponse

interface GetAllCurrenciesUseCase {

    suspend operator fun invoke(): RateResponse?
}