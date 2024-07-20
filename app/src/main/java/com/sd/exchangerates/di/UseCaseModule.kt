package com.sd.exchangerates.di

import com.sd.exchangerates.domain.usecase.GetResultSumCurrencyUseCase
import com.sd.exchangerates.domain.usecase.GetResultSumCurrencyUseCaseImpl
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
    fun bindsUseCase(impl: GetResultSumCurrencyUseCaseImpl): GetResultSumCurrencyUseCase
}