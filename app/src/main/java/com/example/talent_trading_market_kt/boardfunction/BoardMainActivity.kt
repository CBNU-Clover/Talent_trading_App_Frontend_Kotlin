package com.example.talent_trading_market_kt.boardfunction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction.ReadMyBoardActivity

class BoardMainActivity : AppCompatActivity() {
    lateinit var make_content:Button
    lateinit var read_content:Button
    lateinit var search:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        make_content=findViewById(R.id.make_content)
        //read_content=findViewById(R.id.read_content)
        //search=findViewById(R.id.search)


        make_content.setOnClickListener {
            val intent= Intent(this, MakeBoardActivity::class.java)
            startActivity(intent)
        }
        read_content.setOnClickListener {
            val intent= Intent(this, ReadMyBoardActivity::class.java)
            startActivity(intent)
        }
       /* search.setOnClickListener {
            val intent= Intent(this, SearchBoardActivity::class.java)
            startActivity(intent)
        }*/


    }
}