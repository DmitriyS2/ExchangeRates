package com.sd.exchangerates.data

import com.sd.exchangerates.domain.dto.Rate
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("latest.js")
    suspend fun getRate(): Response<Rate?>
}