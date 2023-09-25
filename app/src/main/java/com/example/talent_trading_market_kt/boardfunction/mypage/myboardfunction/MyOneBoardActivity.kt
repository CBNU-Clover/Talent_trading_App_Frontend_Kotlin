package com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.talent_trading_market_kt.MainActivity
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostDeleteBoard
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostReadResponse
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.example.talent_trading_market_kt.reviewfunction.allreview.AllReview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyOneBoardActivity : AppCompatActivity() {
    lateinit var title: TextView
    lateinit var content: TextView
    lateinit var writer:TextView
    lateinit var date:TextView
    lateinit var delete: ImageButton
    lateinit var price:TextView
    lateinit var backbt_myoneboard:ImageButton
    lateinit var go_review_bt:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myboard_read)
        val Id:Long
        Id= intent.getStringExtra("my_postId").toString().toLong()
        delete=findViewById(R.id.delete_button)
        writer=findViewById(R.id.mypage_writer)
        title=findViewById(R.id.mypage_title)
        date=findViewById(R.id.mypage_date)
        price=findViewById(R.id.myboard_price)
        content=findViewById(R.id.myboard_content)
        backbt_myoneboard=findViewById(R.id.myboard_back_button)
        go_review_bt=findViewById(R.id.myboard_review_bt)

        //backbt_myoneboard=findViewById(R.id.backbt_myoneboard)
        backbt_myoneboard.setOnClickListener {
            finish()
        }
        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)

        if(service!=null)
        {
            service.readPost(Id).enqueue(object : Callback<PostReadResponse> {
                override fun onResponse(call: Call<PostReadResponse>, response: Response<PostReadResponse>) {
                    if (response.isSuccessful) {
                        var post: PostReadResponse
                        post=response.body()!!
                        writer.text=post.writerNickname
                        title.text=post.postName
                        content.text=post.content
                        date.text=post.date
                        price.text=post.price.toString()+"원"
                    }
                }

                override fun onFailure(call: Call<PostReadResponse?>, t: Throwable) {

                }

            })
        }
        go_review_bt.setOnClickListener {
            val intent=Intent(this@MyOneBoardActivity, AllReview::class.java)
            intent.putExtra("postId",Id.toString())
            startActivity(intent)
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
                            postDeleteBoard.delete_id= Id.toString()
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


