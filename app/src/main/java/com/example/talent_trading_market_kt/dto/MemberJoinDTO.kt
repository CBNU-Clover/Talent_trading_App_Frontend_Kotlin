package com.example.talent_trading_market_kt.dto

import retrofit2.http.POST
import retrofit2.Retrofit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.gson.GsonConverterFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.content.Intent
import android.widget.EditText
import android.widget.Toast


class MemberJoinDTO {
    var nickname: String? = null
    var email: String? = null
    var name: String? = null
    var passWord: String? = null
    var phoneNumber: String? = null
}