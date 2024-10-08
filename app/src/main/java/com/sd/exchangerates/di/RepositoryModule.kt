package com.sd.exchangerates.di

import com.sd.exchangerates.data.RepositoryImpl
import com.sd.exchangerates.domain.repository.Repository
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
    fun bindsRepository(impl: RepositoryImpl): Repository
}