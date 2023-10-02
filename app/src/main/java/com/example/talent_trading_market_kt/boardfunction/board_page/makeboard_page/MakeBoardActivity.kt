package com.example.talent_trading_market_kt.boardfunction.board_page.makeboard_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.talent_trading_market_kt.MainActivity
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction.ReadMyBoardActivity
import com.example.talent_trading_market_kt.boardfunction.postsearch.SearchBoardAdapter
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostBoardDTO
import com.example.talent_trading_market_kt.fragment.Fragment2_Board
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MakeBoardActivity : AppCompatActivity() {
    lateinit var postName: EditText
    lateinit var content: EditText
    lateinit var write_bt:Button
    lateinit var makeboard_price:EditText
    lateinit var backbt_makeboard:ImageButton
    lateinit var searchBoardAdapter: SearchBoardAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board_write_page)
        postName = findViewById(R.id.title)
        content = findViewById(R.id.content)
        write_bt = findViewById(R.id.make_content)
        makeboard_price=findViewById(R.id.makeboard_price)
        backbt_makeboard=findViewById(R.id.backbt_makeboard)

        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)
        backbt_makeboard.setOnClickListener {
            finish()
        }

        write_bt.setOnClickListener {
            val postname=postName.text.toString()
            val content=content.text.toString()
            val price=makeboard_price.text.toString()
            val makeprice=price.toLong()
            val postBoardDTO= PostBoardDTO()
            postBoardDTO.writerNickname="writer"
            postBoardDTO.postName=postname
            postBoardDTO.content=content
            postBoardDTO.price=makeprice
            if(service!=null)
            {
                service.make_board(postBoardDTO).enqueue(object : Callback<Long?> {
                    override fun onResponse(call: Call<Long?>, response: Response<Long?>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@MakeBoardActivity, "게시물 쓰기 완료", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<Long?>, t: Throwable) {
                        Toast.makeText(this@MakeBoardActivity, "다시 버튼을 눌러주세요", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
            }
        }
    }
}