package com.example.talent_trading_market_kt.boardfunction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.dto.NickCheckDTO
import com.example.talent_trading_market_kt.dto.PostBoardDTO
import com.example.talent_trading_market_kt.dto.PostDeleteBoard
import com.example.talent_trading_market_kt.memberfunction.MemberFunctionApi
import com.example.talent_trading_market_kt.memberfunction.RegisterMember
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Level
import java.util.logging.Logger

class MakeBoardActivity : AppCompatActivity() {
    lateinit var writerNickname: EditText
    lateinit var postName: EditText
    lateinit var content: EditText
    lateinit var write_bt:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_board)
        writerNickname = findViewById(R.id.writer)
        postName = findViewById(R.id.title)
        content = findViewById(R.id.content)
        write_bt = findViewById(R.id.make_content)

        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)

        write_bt.setOnClickListener {
            val writer=writerNickname.text.toString()
            val postname=postName.text.toString()
            val content=content.text.toString()
            val postBoardDTO= PostBoardDTO()
            postBoardDTO.writerNickname=writer
            postBoardDTO.postName=postname
            postBoardDTO.content=content
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