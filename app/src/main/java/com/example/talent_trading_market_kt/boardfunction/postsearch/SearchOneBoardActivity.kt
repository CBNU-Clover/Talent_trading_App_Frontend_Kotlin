
package com.example.talent_trading_market_kt.boardfunction.postsearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.chatfunction.ChatActivity
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostReadResponse
import com.example.talent_trading_market_kt.response.postresponse.PostSearchResult
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchOneBoardActivity : AppCompatActivity() {
    lateinit var writerNickname:TextView
    lateinit var title:TextView
    lateinit var content:TextView
    lateinit var board_price:TextView
    lateinit var searchone_date:TextView
    lateinit var searchone_content:TextView
    lateinit var back_button:ImageButton
    lateinit var chat_button:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.one_board_page)
        var Id:Long
        writerNickname=findViewById(R.id.searchone_writer)
        title=findViewById(R.id.searchone_title)
        chat_button=findViewById(R.id.chat)
        content=findViewById(R.id.searchone_content)
        //payment_button=findViewById(R.id.payment_button)
        board_price=findViewById(R.id.board_price)
        searchone_date=findViewById(R.id.searchone_date)
        searchone_content=findViewById(R.id.searchone_content)
        back_button=findViewById(R.id.back_button)
        Id= intent.getStringExtra("Search_Id").toString().toLong()
        back_button.setOnClickListener {
            finish()
        }
        chat_button.setOnClickListener {
            val intent=Intent(this,ChatActivity::class.java)
            startActivity(intent)
        }
        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)

        if(service!=null)
        {
            service.readPost(Id).enqueue(object : Callback<PostReadResponse> {
                override fun onResponse(call: Call<PostReadResponse>, response: Response<PostReadResponse>) {
                    if (response.isSuccessful) {
                        var post:PostReadResponse
                        post=response.body()!!
                        writerNickname.text=post.writerNickname
                        title.text=post.postName
                        content.text=post.content
                        searchone_date.text=post.date
                        board_price.text=post.price.toString()+"원"
                    }
                }

                override fun onFailure(call: Call<PostReadResponse?>, t: Throwable) {

                }

            })
        }


        /* payment_button.setOnClickListener {
             //val intent=Intent(this,PayMentActivity::class.java)
             intent.putExtra("pay_title",title.text)
             intent.putExtra("pay_price",board_price.text)
             intent.putExtra("pay_Id",Id)
             intent.putExtra("pay_date",searchone_date.text)
             intent.putExtra("pay_content",searchone_content.text)
             startActivity(intent)
         }*/
        /*if(intent.hasExtra("Search_writerNickname"))
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
        }*/

    }
}


