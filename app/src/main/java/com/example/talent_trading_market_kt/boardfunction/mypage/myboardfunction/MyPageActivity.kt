package com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.pointfunction.point_history.MyPointActivity
import com.example.talent_trading_market_kt.tradingfunction.trading_history.TradingHistoryActivity
import kotlinx.android.synthetic.main.mypage.*


class MyPageActivity : AppCompatActivity() {
    lateinit var point_bt:TextView
    lateinit var myboard:TextView
    lateinit var trading_history_bt:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage)
        point_bt=findViewById(R.id.mypoint_btn)
        myboard=findViewById(R.id.history_btn)
        trading_history_bt=findViewById(R.id.trading_history_bt)
        //거래기록 버튼
        trading_history_bt.setOnClickListener {
            val intent = Intent(this, TradingHistoryActivity::class.java)
            startActivity(intent)
        }

        //내 글목록 버튼
        history_btn.setOnClickListener {
            val intent = Intent(this, ReadMyBoardActivity::class.java)
            startActivity(intent)
        }

        //포인트 버튼
        point_bt.setOnClickListener {
            val intent = Intent(this, MyPointActivity::class.java)
            startActivity(intent)
        }
    }
}


