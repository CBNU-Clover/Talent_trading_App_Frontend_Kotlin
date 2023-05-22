package com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import com.example.talent_trading_market_kt.MainActivity
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.BoardFunctionApi
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostDeleteBoard
import com.example.talent_trading_market_kt.fragment.Fragment3_MyPage
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OneBoardActivity : AppCompatActivity() {
    lateinit var title: TextView
    lateinit var content: TextView
    lateinit var delete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boardread)
        val id:Long
        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)
        title=findViewById(R.id.one_title)
        content=findViewById(R.id.one_content)
        delete=findViewById(R.id.delete_button)
        /*println(intent.getStringExtra("postName"))
        println(intent.getStringExtra("content"))
        println(intent.getStringExtra("Id"))
        println("++++++++++++++++++++++++++++++++++++++++++++++++++++")*/
        if(intent.hasExtra("postName"))
        {
            title.text=intent.getStringExtra("postName")

        }
        if(intent.hasExtra("content"))
        {
            content.text=intent.getStringExtra("content")
        }
        delete.setOnClickListener {
            if(service!=null)
                {
                    val postDeleteBoard= PostDeleteBoard()
                    postDeleteBoard.delete_id= intent.getStringExtra("Id")
                    service.deletePost(postDeleteBoard).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@OneBoardActivity, "게시물 삭제 완료", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@OneBoardActivity, MainActivity::class.java)
                                finishAffinity()
                                startActivity(intent)
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(this@OneBoardActivity, "다시 버튼을 눌러주세요", Toast.LENGTH_SHORT)
                                .show()
                        }

                    })
                }
            }
        }
    }


