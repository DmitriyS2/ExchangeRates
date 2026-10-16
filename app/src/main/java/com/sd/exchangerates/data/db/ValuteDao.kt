package com.sd.exchangerates.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ValuteDao {

    @Query("SELECT * FROM ValuteEntity")
    suspend fun getAllValutes(): List<ValuteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertValutes(valutes: List<ValuteEntity>)
}