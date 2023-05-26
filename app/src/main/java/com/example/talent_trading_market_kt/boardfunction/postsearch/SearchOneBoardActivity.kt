
package com.example.talent_trading_market_kt.boardfunction.postsearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.payfunction.PayMentActivity
import org.w3c.dom.Text

class SearchOneBoardActivity : AppCompatActivity() {
    lateinit var writerNickname:TextView
    lateinit var title:TextView
    lateinit var content:TextView
    lateinit var payment_button:Button
    lateinit var board_price:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.one_search_board)
        var id:Long
        writerNickname=findViewById(R.id.searchone_writer)
        title=findViewById(R.id.searchone_title)
        content=findViewById(R.id.searchone_content)
        payment_button=findViewById(R.id.payment_button)
        board_price=findViewById(R.id.board_price)
        payment_button.setOnClickListener {
            val intent=Intent(this,PayMentActivity::class.java)
            intent.putExtra("pay_title",title.text)
            intent.putExtra("pay_price",board_price.text)
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
            board_price.text=intent.getStringExtra("Search_price")
        }
    }
}


