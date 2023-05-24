package com.example.talent_trading_market_kt.tradingfunction

import com.example.talent_trading_market_kt.dto.memberfunctiondto.LoginDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TradingFunctionApi {

    @POST("/api/vi/Trading_Talent/trade")
    fun trade(@Body loginDTO: LoginDTO?): Call<String?>
}