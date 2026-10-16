package com.sd.exchangerates.di

import android.content.Context
import androidx.room.Room
import com.sd.exchangerates.data.db.AppDatabase
import com.sd.exchangerates.data.db.ValuteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    private const val DATABASE_NAME = "exchange_rates_db"

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideRateDao(database: AppDatabase): ValuteDao {
        return database.valuteDao()
    }
}