package com.example.talent_trading_market_kt.boardfunction

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.response.PostGetAllBoard
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import java.io.Serializable

class OneBoardActivity : AppCompatActivity() {
    lateinit var title: TextView
    lateinit var content: TextView
    lateinit var delete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_board)
        val id:Long
        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)
        title=findViewById(R.id.one_title)
        content=findViewById(R.id.one_content)
        delete=findViewById(R.id.delete_bt)
        println(intent.getStringExtra("postName"))
        println(intent.getStringExtra("content"))
        println(intent.getStringExtra("Id"))
        println("++++++++++++++++++++++++++++++++++++++++++++++++++++")
        if(intent.hasExtra("postName"))
        {
            title.text=intent.getStringExtra("postName")

        }
        if(intent.hasExtra("content"))
        {
            content.text=intent.getStringExtra("content")
        }
        delete.setOnClickListener {

        }
    }
}

