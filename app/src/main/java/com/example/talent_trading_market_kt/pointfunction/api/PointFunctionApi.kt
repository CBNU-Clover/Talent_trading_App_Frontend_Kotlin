package com.example.talent_trading_market_kt.pointfunction.api

import com.example.talent_trading_market_kt.dto.pointfunctiondto.ChargePointDTO
import com.example.talent_trading_market_kt.response.pointresponse.PointHistory
import com.example.talent_trading_market_kt.response.pointresponse.ShowPointDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PointFunctionApi{
    @POST("/api/vi/Point/charge_point")
    fun charge_point(@Body chargePoint: ChargePointDTO): Call<Void>

    @GET("/api/vi/Point/show_point")
    fun show_point():Call<ShowPointDTO>

    @GET("/api/vi/Point/point_history")
    fun point_history():Call<List<PointHistory>>
}