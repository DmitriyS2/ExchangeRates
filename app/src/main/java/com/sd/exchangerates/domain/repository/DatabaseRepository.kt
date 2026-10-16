package com.sd.exchangerates.domain.repository

import com.sd.exchangerates.domain.dto.Rate

interface DatabaseRepository {

    suspend fun getRateFromDB(): Rate?

    suspend fun insertValutes(rate: Rate)
}