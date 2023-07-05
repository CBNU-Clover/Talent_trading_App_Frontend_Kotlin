package com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.talent_trading_market_kt.MainActivity
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostDeleteBoard
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.myboard_read.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyOneBoardActivity : AppCompatActivity() {
    lateinit var title: TextView
    lateinit var content: TextView
    lateinit var delete: Button
    lateinit var readboard_price:TextView
    lateinit var writer_nickname:TextView
    lateinit var backbt_myoneboard:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myboard_read)
        val id:Long
        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)
        title=findViewById(R.id.one_title)
        content=findViewById(R.id.one_content)
        delete=findViewById(R.id.delete_button)
        readboard_price=findViewById(R.id.readboard_price)
        writer_nickname=findViewById(R.id.writer_nickname)
        backbt_myoneboard=findViewById(R.id.backbt_myoneboard)
        backbt_myoneboard.setOnClickListener {
            finish()
        }
        if(intent.hasExtra("postName"))
        {
            title.text=intent.getStringExtra("postName")

        }
        if(intent.hasExtra("content"))
        {
            content.text=intent.getStringExtra("content")
        }
        if(intent.hasExtra("price"))
        {
            readboard_price.text=intent.getStringExtra("price")+"원"
        }
        if(intent.hasExtra("date"))
        {
            my_board_price.text=intent.getStringExtra("date")
        }
        if(intent.hasExtra("writer_nickname"))
        {
            writer_nickname.text=intent.getStringExtra("writer_nickname")

        }

        delete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("알림")
                .setMessage("정말 게시물을 삭제하시겠습니까?")
                .setPositiveButton("삭제",
                    DialogInterface.OnClickListener { dialog, id ->
                        if(service!=null)
                        {
                            val postDeleteBoard= PostDeleteBoard()
                            postDeleteBoard.delete_id= intent.getStringExtra("Id")
                            service.deletePost(postDeleteBoard).enqueue(object : Callback<Void> {
                                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                    if (response.isSuccessful) {
                                        Toast.makeText(this@MyOneBoardActivity, "게시물 삭제 완료", Toast.LENGTH_SHORT).show()

                                        val intent = Intent(this@MyOneBoardActivity, MainActivity::class.java)
                                        finishAffinity()
                                        startActivity(intent)
                                    }
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    Toast.makeText(this@MyOneBoardActivity, "다시 버튼을 눌러주세요", Toast.LENGTH_SHORT)
                                        .show()
                                }

                            })
                        }
                    })
                .setNegativeButton("취소",
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            builder.create()
            builder.show()

            }
        }
    }


