package com.example.talent_trading_market_kt.reviewfunction.api

import com.example.talent_trading_market_kt.reviewfunction.dto.ReviewReadResponse
import com.example.talent_trading_market_kt.reviewfunction.dto.ReviewWriteDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ReviewFunctionApi {
    //리뷰 작성 api
    @POST("api/vi/review/write")
    fun ReviewWrite(@Body reviewWriteDTO: ReviewWriteDTO?): Call<String?>

    //게시물마다의 모든 리뷰 가져오기 api
    @GET("/api/vi/review/getAllPostReview/{postId}")
    fun getAllPostReview(@Path("postId") postId:Long): Call<List<ReviewReadResponse>>

    @GET("/api/vi/review/getPostAvg/{postId}")
    fun getPostAvg(@Path("postId") postId:Long): Call<Double>
}