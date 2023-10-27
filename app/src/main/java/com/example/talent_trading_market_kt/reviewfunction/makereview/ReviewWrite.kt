package com.example.talent_trading_market_kt.reviewfunction.makereview

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostReadResponse
import com.example.talent_trading_market_kt.retrofit.App
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.example.talent_trading_market_kt.reviewfunction.api.ReviewFunctionApi
import com.example.talent_trading_market_kt.reviewfunction.dto.ReviewWriteDTO
import kotlinx.android.synthetic.main.review_write_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewWrite : AppCompatActivity() {
    lateinit var makereview_bt:Button
    lateinit var rv_write_title:TextView
    lateinit var rv_write_price:TextView
    lateinit var rv_write_backbt:ImageButton
    lateinit var rv_post_image:ImageView
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.review_write_page)
    var Id: Long
    val ratingBar:RatingBar=findViewById(R.id.ratingBar)
    rv_write_title=findViewById(R.id.rv_write_title)
    rv_write_price=findViewById(R.id.rv_write_price)
    rv_write_backbt=findViewById(R.id.rv_write_backbt)
    rv_post_image=findViewById(R.id.review_post_image)
    Id = intent.getStringExtra("postId").toString().toLong()
    var userRating:Long=0
    rv_write_backbt.setOnClickListener {
        finish()
    }
    makereview_bt = findViewById(R.id.make_review)
    ratingBar.onRatingBarChangeListener=
        RatingBar.OnRatingBarChangeListener{
            ratingBar, rating, fromUser ->
            userRating= rating.toLong()
        }
    val service2 = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)
    if(service2!=null)
    {
        service2.readPost(Id).enqueue(object : Callback<PostReadResponse> {
            override fun onResponse(call: Call<PostReadResponse>, response: Response<PostReadResponse>) {
                if (response.isSuccessful) {
                    var post: PostReadResponse
                    post=response.body()!!
                    rv_write_title.text=post.postName
                    rv_write_price.text=post.price.toString()+"원"
                    Glide.with(this@ReviewWrite)
                        .load(App.prefs.image+post.board_image_url.toString())
                        .dontAnimate()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .into(rv_post_image)
                }
            }

            override fun onFailure(call: Call<PostReadResponse?>, t: Throwable) {

            }

        })
    }

    val service = RetrofitConnection.getInstance().create(ReviewFunctionApi::class.java)
    makereview_bt.setOnClickListener {
        val review_content = review_content.text.toString()
        val reviewWriteDTO = ReviewWriteDTO()
        reviewWriteDTO.postId = Id
        reviewWriteDTO.content = review_content
        reviewWriteDTO.starRating =userRating
        if (service != null) {
            service.ReviewWrite(reviewWriteDTO).enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ReviewWrite, "리뷰작성 완료", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    Toast.makeText(this@ReviewWrite, "시스템 오류", Toast.LENGTH_SHORT)
                        .show()
                }

            })


        }

        //별표 표시에 따라 starRating 값이 달라진다
        //내용 입력 후
    }
 }
}