package com.example.talent_trading_market_kt.boardfunction

import com.example.talent_trading_market_kt.dto.*
import com.example.talent_trading_market_kt.response.PostGetAllBoard
import retrofit2.Call
import retrofit2.http.*

interface BoardFunctionApi{

    @POST("/api/vi/boards/write")
    fun make_board(@Body postBoardDTO: PostBoardDTO?):Call<Long?>

    @GET("/api/vi/boards/getAllboard")
    fun getAllboard():Call<List<PostGetAllBoard>>

    @POST("/api/vi/boards/deletePost")
    fun deletePost(@Body postDeleteBoard: PostDeleteBoard):Call<Void>
}