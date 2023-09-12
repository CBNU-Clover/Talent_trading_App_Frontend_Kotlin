package com.example.talent_trading_market_kt.chatfunction

import retrofit2.Call
import retrofit2.http.GET

interface ChatFunctionApi {
    @GET("/api/vi/chat/chat_history/376")
    fun ChatHistory(): Call<List<ChatHistoryDTO>>
}