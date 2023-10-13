package com.example.talent_trading_market_kt.tradingfunction.api

import com.example.talent_trading_market_kt.dto.memberfunctiondto.LoginDTO
import com.example.talent_trading_market_kt.response.trade.TradingHistory.TradingHistory
import com.example.talent_trading_market_kt.tradingfunction.dto.TradePost
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TradingFunctionApi {

    @POST("/api/vi/Trading_Talent/trade")
    fun trade(@Body tradePost: TradePost): Call<Void>

    @GET("/api/vi/Trading_Talent/trading_history")
    fun trade_history():Call<List<TradingHistory>>
}