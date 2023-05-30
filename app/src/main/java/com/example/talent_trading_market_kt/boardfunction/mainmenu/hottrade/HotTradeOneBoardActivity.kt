
package com.example.talent_trading_market_kt.boardfunction.mainmenu.hottrade

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.payfunction.PayMentActivity
import org.w3c.dom.Text

class HotTradeOneBoardActivity : AppCompatActivity() {
    lateinit var writerNickname:TextView
    lateinit var title:TextView
    lateinit var content:TextView
    lateinit var payment_button:Button
    lateinit var board_price:TextView
    lateinit var date:TextView
    lateinit var back_button:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.one_search_board)
        var Id:String
        writerNickname=findViewById(R.id.searchone_writer)
        title=findViewById(R.id.searchone_title)
        content=findViewById(R.id.searchone_content)
        payment_button=findViewById(R.id.payment_button)
        board_price=findViewById(R.id.board_price)
        date=findViewById(R.id.searchone_date)
        back_button=findViewById(R.id.back_button)
        Id= intent.getStringExtra("HotTrade_Id").toString()
        back_button.setOnClickListener {
            finish()
        }
        payment_button.setOnClickListener {
            val intent=Intent(this,PayMentActivity::class.java)
            intent.putExtra("pay_title",title.text)
            intent.putExtra("pay_price",board_price.text)
            intent.putExtra("pay_Id",Id)
            intent.putExtra("pay_date",date.text)
            intent.putExtra("pay_content",content.text)
            startActivity(intent)
        }
        if(intent.hasExtra("HotTrade_writerNickname"))
        {
            writerNickname.text=intent.getStringExtra("HotTrade_writerNickname")

        }
        if(intent.hasExtra("HotTrade_postName"))
        {
            title.text=intent.getStringExtra("HotTrade_postName")

        }
        if(intent.hasExtra("HotTrade_content"))
        {
            content.text=intent.getStringExtra("HotTrade_content")
        }
        if(intent.hasExtra("HotTrade_price"))
        {
            board_price.text=intent.getStringExtra("HotTrade_price")+"원"
        }
        if(intent.hasExtra("HotTrade_date"))
        {
            date.text=intent.getStringExtra("HotTrade_date")
        }

    }
}


