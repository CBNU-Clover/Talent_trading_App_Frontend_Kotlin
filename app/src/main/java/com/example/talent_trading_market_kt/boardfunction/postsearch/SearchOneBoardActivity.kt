
package com.example.talent_trading_market_kt.boardfunction.postsearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostReadResponse
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.example.talent_trading_market_kt.reviewfunction.makereview.ReviewWrite
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
            val intent=Intent(this,ReviewWrite::class.java)
            intent.putExtra("postId",Id.toString())
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

    }
}


