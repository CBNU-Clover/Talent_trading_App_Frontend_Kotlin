package com.example.talent_trading_market_kt.dto.tradefunctiondto

import retrofit2.Call
import retrofit2.http.*

interface TradingFunctionApi{

    @POST("/api/vi/Trading_Talent/trade")
    fun trade(@Body tradePost: TradePost?):Call<Void>
}