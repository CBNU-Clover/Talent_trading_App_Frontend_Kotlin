package com.example.talent_trading_market_kt.memberfunction.api

import com.example.talent_trading_market_kt.dto.memberfunctiondto.EmailCheckDTO
import com.example.talent_trading_market_kt.dto.memberfunctiondto.LoginDTO
import com.example.talent_trading_market_kt.dto.memberfunctiondto.MemberJoinDTO
import com.example.talent_trading_market_kt.dto.memberfunctiondto.NickCheckDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberFunctionApi{

   @POST("api/vi/members/login")
   fun login(@Body loginDTO: LoginDTO?): Call<String?>

    @POST("/api/vi/members/join")
    fun join(@Body memberjoinDTO: MemberJoinDTO?): Call<String?>

    @POST("/api/vi/members/check_email")
    fun check_email(@Body emailCheckDTO: EmailCheckDTO?): Call<String?>

    @POST("/api/vi/members/check_nickname")
    fun check_Nick(@Body nickCheckDTO: NickCheckDTO?): Call<String?>
}