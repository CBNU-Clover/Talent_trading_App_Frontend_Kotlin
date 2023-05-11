package com.example.talent_trading_market_kt.boardfunction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.memberfunction.LoginActivity

class BoardMainActivity : AppCompatActivity() {
    lateinit var make_content:Button
    lateinit var read_content:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_main)

        make_content=findViewById(R.id.make_content)
        read_content=findViewById(R.id.read_content)


        make_content.setOnClickListener {
            val intent= Intent(this, MakeBoardActivity::class.java)
            startActivity(intent)
        }
        read_content.setOnClickListener {
            val intent= Intent(this, ReadBoardActivity::class.java)
            startActivity(intent)
        }
    }
}