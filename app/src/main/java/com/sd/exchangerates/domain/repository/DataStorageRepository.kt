package com.sd.exchangerates.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStorageRepository {

    val createRequestToBE: Flow<String>
    val dateResponseFromBE: Flow<String>

    suspend fun setCreateRequest(createRequest: String)
    suspend fun setDateResponseFromBE(dateResponse: String)
}