package com.example.talent_trading_market_kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MyPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        //내 글목록 눌렀을때 이동 ->ReadBoardActivity , 한 게시물 눌렀을때 OneBoardActivity
    }
}