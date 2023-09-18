package com.example.talent_trading_market_kt.reviewfunction.api

import com.example.talent_trading_market_kt.reviewfunction.dto.ReviewWriteDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ReviewFunctionApi {
    @POST("api/vi/review/write")
    fun ReviewWrite(@Body reviewWriteDTO: ReviewWriteDTO?): Call<String?>
}