package com.sd.exchangerates.di

import com.sd.exchangerates.data.datastore.DataStorageImpl
import com.sd.exchangerates.domain.repository.DataStorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataStorageModule {

    @Singleton
    @Binds
    abstract fun bindDataStorage(impl: DataStorageImpl): DataStorageRepository
}