package com.sd.exchangerates.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sd.exchangerates.domain.repository.DataStorageRepository
import com.sd.exchangerates.util.EMPTY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStorageRepository {

    override val createRequestToBE: Flow<String>
        get() = dataStore.data
            .catch { e ->
                if (e is IOException) emit(emptyPreferences())
                else throw e
            }
            .map { it[createRequestKey] ?: DEFAULT_CREATE_REQUEST }

    override val dateResponseFromBE: Flow<String>
        get() = dataStore.data
            .catch { e ->
                if (e is IOException) emit(emptyPreferences())
                else throw e
            }
            .map { it[dateResponseKey] ?: DEFAULT_DATE_RESPONSE }

    override suspend fun setCreateRequest(createRequest: String) {
        dataStore.edit { it[createRequestKey] = createRequest }
    }

    override suspend fun setDateResponseFromBE(dateResponse: String) {
        dataStore.edit { it[dateResponseKey] = dateResponse }
    }

    companion object {
        private const val CREATE_REQUEST_KEY = "create_request_key"
        private const val DATE_RESPONSE_KEY = "date_response_key"
        private const val DEFAULT_CREATE_REQUEST = EMPTY
        private const val DEFAULT_DATE_RESPONSE = EMPTY
        private val createRequestKey = stringPreferencesKey(CREATE_REQUEST_KEY)
        private val dateResponseKey = stringPreferencesKey(DATE_RESPONSE_KEY)
    }
}