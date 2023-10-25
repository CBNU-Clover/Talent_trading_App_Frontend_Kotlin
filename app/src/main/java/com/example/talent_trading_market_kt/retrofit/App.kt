package com.example.talent_trading_market_kt.retrofit

import android.app.Application

class App : Application(){
    companion object{
        lateinit var prefs:Prefs
    }
    override fun onCreate() {
        prefs=Prefs(applicationContext)
        prefs.serverUrl = "http://192.168.45.103:8080"
        prefs.image= prefs.serverUrl+"/api/vi/image/image/"
        super.onCreate()
    }
}