package com.example.talent_trading_market_kt.retrofit

import android.app.Application

class App : Application(){
    companion object{
        lateinit var prefs:Prefs
    }
    override fun onCreate() {
        prefs=Prefs(applicationContext)
        prefs.serverUrl = "http://cloverx.kro.kr:10003/"
        prefs.image= prefs.serverUrl+"api/vi/image/image/"
        super.onCreate()
    }
}