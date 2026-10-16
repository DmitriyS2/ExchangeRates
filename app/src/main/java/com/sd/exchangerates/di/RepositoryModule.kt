package com.sd.exchangerates.di

import com.sd.exchangerates.data.db.DatabaseRepositoryImpl
import com.sd.exchangerates.data.network.NetworkRepositoryImpl
import com.sd.exchangerates.domain.repository.DatabaseRepository
import com.sd.exchangerates.domain.repository.NetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsNetworkRepository(impl: NetworkRepositoryImpl): NetworkRepository

    @Binds
    @Singleton
    fun bindsDbRepository(impl: DatabaseRepositoryImpl): DatabaseRepository
}