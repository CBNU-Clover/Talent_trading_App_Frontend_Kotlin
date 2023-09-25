package com.example.talent_trading_market_kt.retrofit

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Prefs(context: Context) {
    private val prefNm="mPref"
    private val prefs=context.getSharedPreferences(prefNm,MODE_PRIVATE)
    var token:String?
        get() = prefs.getString("token",null)
        set(value) {
            prefs.edit().putString("token", value).apply()
        }
    var nickname:String?
    get()=prefs.getString("nickname",null)
        set(value) {
            prefs.edit().putString("nickname", value).apply()
        }
}