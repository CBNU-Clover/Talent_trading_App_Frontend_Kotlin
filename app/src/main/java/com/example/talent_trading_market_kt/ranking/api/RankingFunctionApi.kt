package com.example.talent_trading_market_kt.ranking.api

import com.example.talent_trading_market_kt.dto.boardfunctiondto.*
import com.example.talent_trading_market_kt.ranking.dto.MyRanking
import com.example.talent_trading_market_kt.ranking.dto.ResponseRankingList
import com.example.talent_trading_market_kt.response.PostGetAllBoard
import com.example.talent_trading_market_kt.response.postresponse.PostSearchResult
import retrofit2.Call
import retrofit2.http.*

interface RankingFunctionApi {


    @GET("/api/vi/ranking/ranking")
    fun Ranking(): Call<List<ResponseRankingList>>

    @GET("/api/vi/ranking/myranking")
    fun myRanking(): Call<MyRanking>
}