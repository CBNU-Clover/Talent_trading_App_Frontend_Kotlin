package com.example.talent_trading_market_kt.retrofit

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitConnection {
    var INSTANCE: Retrofit? = null
        private set
    // 객체를 하나만 생성하는 싱글톤 패턴을 적용합니다.
    companion object {
        // API 서버의 주소가 BASE_URL이 됩니다.
        private const val BASE_URL ="http://192.168.45.251:8080"/*"http://gk417gun.kro.kr:10003/"*/
        private var INSTANCE: Retrofit? = null
        private val okHttpClient = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build() //Client 생성


        fun getInstance(): Retrofit {
            val gson = GsonBuilder().setLenient().create();
            if(INSTANCE == null) {  // null인 경우에만 생성
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)  // API 베이스 URL 설정
                    .client(okHttpClient) //Client와 retrofit의 인스턴스를 연결
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }

            return INSTANCE!!
        }



    }
    //Interceptor 생성
    class AuthInterceptor : Interceptor {
        override fun intercept(chain:Interceptor.Chain): Response {
            var req =
                chain.request().newBuilder().addHeader("Authorization",App.prefs.token ?: "").build()
            return chain.proceed(req)
        }
    }
}
