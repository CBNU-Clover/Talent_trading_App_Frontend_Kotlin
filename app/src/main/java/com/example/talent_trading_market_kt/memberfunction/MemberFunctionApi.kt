package com.example.talent_trading_market_kt.memberfunction

import com.example.talent_trading_market_kt.dto.EmailCheckDTO
import com.example.talent_trading_market_kt.dto.LoginDTO
import com.example.talent_trading_market_kt.dto.MemberJoinDTO
import com.example.talent_trading_market_kt.dto.NickCheckDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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