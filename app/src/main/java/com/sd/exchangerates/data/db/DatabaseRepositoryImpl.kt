package com.sd.exchangerates.data.db

import com.sd.exchangerates.domain.dto.Rate
import com.sd.exchangerates.domain.dto.Valute
import com.sd.exchangerates.domain.repository.DatabaseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepositoryImpl @Inject constructor(
    private val valuteDao: ValuteDao,
) : DatabaseRepository {

    override suspend fun getRateFromDB(): Rate? {
        val valuteEntities = valuteDao.getAllValutes()
        return if (valuteEntities.isNotEmpty()) {
            val date = valuteEntities.first().date
            val valutes = valuteEntities.map { it.toValute() }
            val valuteMap: Map<String, Valute> = valutes.associateBy { it.code }
            Rate(
                date = date,
                valute = valuteMap
            )
        } else {
            null
        }
    }

    override suspend fun insertValutes(rate: Rate) {
        val date = rate.date
        val valutes: List<Valute> = rate.valute.map { it.value }
        val entities: List<ValuteEntity> = valutes.map { it.toEntity(date) }
        valuteDao.insertValutes(entities)
    }
}