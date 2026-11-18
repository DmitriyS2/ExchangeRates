package com.sd.exchangerates.di

import com.sd.exchangerates.domain.usecase.GetAllCurrenciesUseCase
import com.sd.exchangerates.domain.usecase.GetAllCurrenciesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {
    @Binds
    @Singleton
    fun bindsUseCase(impl: GetAllCurrenciesUseCaseImpl): GetAllCurrenciesUseCase
}