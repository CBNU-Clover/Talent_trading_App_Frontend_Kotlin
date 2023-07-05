
package com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.response.PostGetAllBoard
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.myboardhistory.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadMyBoardActivity : AppCompatActivity() {
    lateinit var backbt_myboard:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myboardhistory)
        backbt_myboard=findViewById(R.id.backbt_myboard)
        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)
        backbt_myboard.setOnClickListener {
            finish()
        }
        if(service!=null)
        {
            service.getAllboard().enqueue(object : Callback<List<PostGetAllBoard>> {
                override fun onResponse(call: Call<List<PostGetAllBoard>>, response: Response<List<PostGetAllBoard>>) {
                    if (response.isSuccessful) {
                        var boardList:List<PostGetAllBoard>;
                        boardList= response.body()!!;
                        myboard_view.layoutManager=LinearLayoutManager(this@ReadMyBoardActivity, LinearLayoutManager.VERTICAL,false)
                        myboard_view.setHasFixedSize(true)
                        myboard_view.adapter=MyBoardAdapter(boardList)
                    }
                }

                override fun onFailure(call: Call<List<PostGetAllBoard>?>, t: Throwable) {
                    Toast.makeText(this@ReadMyBoardActivity, "다시 버튼을 눌러주세요", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }

    }

}
