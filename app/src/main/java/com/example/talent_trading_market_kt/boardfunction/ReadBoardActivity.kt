package com.example.talent_trading_market_kt.boardfunction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.response.PostGetAllBoard
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.activity_read_board.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_board)
        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)
        if(service!=null)
        {
            service.getAllboard().enqueue(object : Callback<List<PostGetAllBoard>> {
                override fun onResponse(call: Call<List<PostGetAllBoard>>, response: Response<List<PostGetAllBoard>>) {
                    if (response.isSuccessful) {
                        var boardList:List<PostGetAllBoard>;
                        boardList= response.body()!!;
                        board_view.layoutManager=LinearLayoutManager(this@ReadBoardActivity,LinearLayoutManager.VERTICAL,false)
                        board_view.setHasFixedSize(true)
                        board_view.adapter=BoardAdapter(boardList)

                    }
                }

                override fun onFailure(call: Call<List<PostGetAllBoard>?>, t: Throwable) {
                    Toast.makeText(this@ReadBoardActivity, "다시 버튼을 눌러주세요", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }


    }

}