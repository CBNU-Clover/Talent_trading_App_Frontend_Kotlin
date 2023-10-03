package com.example.talent_trading_market_kt.chatfunction.api

import com.example.talent_trading_market_kt.chatfunction.dto.ChatHistoryDTO
import com.example.talent_trading_market_kt.chatfunction.dto.ChattingRoomDTO
import com.example.talent_trading_market_kt.chatfunction.response.ChattingRoomListDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatFunctionApi {
    //채팅 기록 가져오기
    @GET("/api/vi/chat/chat_history/{roomId}")
    fun ChatHistory(@Path("roomId") roomId:Long): Call<List<ChatHistoryDTO>>

    //채팅방 만들기
    @POST("/api/vi/chat/create_room")
    fun createRoom(@Body chattingRoomDTO: ChattingRoomDTO):Call<Long>
    
    //채팅방 리스트 가져오기
    @GET("/api/vi/chat/rooms")
    fun Rooms():Call<List<ChattingRoomListDTO>>

    //그 게시글이 이미 채팅방이 존재하는지 검사
    @GET("/api/vi/chat/confirm_chattingroom/{postId}")
    fun confirmChattingRoom(@Path("postId")postId:Long):Call<Int>

}