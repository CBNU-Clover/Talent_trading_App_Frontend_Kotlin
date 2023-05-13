package com.example.talent_trading_market_kt.boardfunction

import com.example.talent_trading_market_kt.dto.*
import com.example.talent_trading_market_kt.response.PostGetAllBoard
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BoardFunctionApi{

    @POST("/api/vi/boards/write")
    fun make_board(@Body postBoardDTO: PostBoardDTO?):Call<Long?>

    @GET("/api/vi/boards/getAllboard")
    fun getAllboard():Call<List<PostGetAllBoard>>


}