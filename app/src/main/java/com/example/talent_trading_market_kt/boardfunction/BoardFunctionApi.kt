package com.example.talent_trading_market_kt.boardfunction

import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostBoardDTO
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostDeleteBoard
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostSearch
import com.example.talent_trading_market_kt.response.PostGetAllBoard
import com.example.talent_trading_market_kt.response.PostSearchResult
import retrofit2.Call
import retrofit2.http.*

interface BoardFunctionApi{

    @POST("/api/vi/boards/write")
    fun make_board(@Body postBoardDTO: PostBoardDTO?):Call<Long?>

    @GET("/api/vi/boards/getAllboard")
    fun getAllboard():Call<List<PostGetAllBoard>>

    @POST("/api/vi/boards/deletePost")
    fun deletePost(@Body postDeleteBoard: PostDeleteBoard):Call<Void>

    @POST("/api/vi/boards/postsearch")
    fun postsearch(@Body postSearch: PostSearch):Call<List<PostSearchResult>>
}