package com.example.talent_trading_market_kt.boardfunction.api

import com.example.talent_trading_market_kt.dto.boardfunctiondto.*
import com.example.talent_trading_market_kt.response.PostGetAllBoard
import com.example.talent_trading_market_kt.response.postresponse.PostSearchResult
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

    @GET("/api/vi/boards/read/{postId}")
    fun readPost(@Path("postId") roomId:Long):Call<PostReadResponse>

    @GET("/api/vi/popularPost/all")
    fun getAllPopularPost():Call<List<PopularPostResponse>>

    @GET("/api/vi/image/image/{imageId}")
    fun imagetest(@Path("imageId")imageId:Long):Call<ByteArray>

}