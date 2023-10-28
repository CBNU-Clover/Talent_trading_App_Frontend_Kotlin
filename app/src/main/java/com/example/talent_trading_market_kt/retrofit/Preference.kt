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
    var serverUrl: String?
        get() = prefs.getString("serverUrl", null)
        set(value) {
            prefs.edit().putString("serverUrl", value).apply()
        }
    var image: String?
        get() = prefs.getString("image", null)
        set(value) {
            prefs.edit().putString("image", value).apply()
        }
    var point: String?
        get() = prefs.getString("point", null)
        set(value) {
            prefs.edit().putString("point", value).apply()
        }
    var trade_status_complete: String?
        get() = prefs.getString("trade_status", null)
        set(value) {
            prefs.edit().putString("trade_status", value).apply()
        }
    var first_chat_image: String?
        get() = prefs.getString("first_chat_image", null)
        set(value) {
            prefs.edit().putString("first_chat_image", value).apply()
        }
}