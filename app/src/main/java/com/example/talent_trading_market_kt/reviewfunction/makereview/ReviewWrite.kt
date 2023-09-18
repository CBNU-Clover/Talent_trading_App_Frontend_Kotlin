package com.example.talent_trading_market_kt.reviewfunction.makereview

import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.example.talent_trading_market_kt.reviewfunction.api.ReviewFunctionApi
import com.example.talent_trading_market_kt.reviewfunction.dto.ReviewWriteDTO
import kotlinx.android.synthetic.main.review_write_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewWrite : AppCompatActivity() {
    lateinit var makereview_bt:Button
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.review_write_page)
    var Id: Long
    val ratingBar:RatingBar=findViewById(R.id.ratingBar)
    Id = intent.getStringExtra("postId").toString().toLong()
    var userRating:Long=0

    makereview_bt = findViewById(R.id.make_review)
    ratingBar.onRatingBarChangeListener=
        RatingBar.OnRatingBarChangeListener{
            ratingBar, rating, fromUser ->
            userRating= rating.toLong()
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