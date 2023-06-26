
package com.example.talent_trading_market_kt.boardfunction.postsearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.payfunction.PayMentActivity

class SearchOneBoardActivity : AppCompatActivity() {
    lateinit var writerNickname:TextView
    lateinit var title:TextView
    lateinit var content:TextView
    lateinit var payment_button:Button
    lateinit var board_price:TextView
    lateinit var searchone_date:TextView
    lateinit var searchone_content:TextView
    lateinit var back_button:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.one_board_page)
        var Id:String
        writerNickname=findViewById(R.id.searchone_writer)
        title=findViewById(R.id.searchone_title)
        content=findViewById(R.id.searchone_content)
        payment_button=findViewById(R.id.payment_button)
        board_price=findViewById(R.id.board_price)
        searchone_date=findViewById(R.id.searchone_date)
        searchone_content=findViewById(R.id.searchone_content)
        back_button=findViewById(R.id.back_button)
        Id= intent.getStringExtra("Search_Id").toString()
        back_button.setOnClickListener {
            finish()
        }
        payment_button.setOnClickListener {
            val intent=Intent(this,PayMentActivity::class.java)
            intent.putExtra("pay_title",title.text)
            intent.putExtra("pay_price",board_price.text)
            intent.putExtra("pay_Id",Id)
            intent.putExtra("pay_date",searchone_date.text)
            intent.putExtra("pay_content",searchone_content.text)
            startActivity(intent)
        }
        if(intent.hasExtra("Search_writerNickname"))
        {
            writerNickname.text=intent.getStringExtra("Search_writerNickname")

        }
        if(intent.hasExtra("Search_postName"))
        {
            title.text=intent.getStringExtra("Search_postName")

        }
        if(intent.hasExtra("Search_content"))
        {
            content.text=intent.getStringExtra("Search_content")
        }
        if(intent.hasExtra("Search_price"))
        {
            board_price.text=intent.getStringExtra("Search_price")+"원"
        }
        if(intent.hasExtra("Search_date"))
        {
            searchone_date.text=intent.getStringExtra("Search_date")
        }

    }
}


