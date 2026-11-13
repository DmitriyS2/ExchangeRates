package com.sd.exchangerates.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStorageRepository {

    val createRequest: Flow<String>
    suspend fun setCreateRequest(createRequest: String)
}