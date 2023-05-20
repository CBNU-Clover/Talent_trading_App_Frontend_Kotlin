package com.example.talent_trading_market_kt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.talent_trading_market_kt.boardfunction.BoardMainActivity
import com.example.talent_trading_market_kt.memberfunction.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //lateinit var button: Button
    //lateinit var board_bt:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //button=findViewById(R.id.bt)
        //board_bt=findViewById(R.id.board_bt)

      /*  bt.setOnClickListener {

            val intent=Intent(this, LoginActivity::class.java)
                startActivity(intent)

        }*/
        /*board_bt.setOnClickListener {

           val intent=Intent(this, BoardMainActivity::class.java)
            startActivity(intent)
        }*/

    }
}