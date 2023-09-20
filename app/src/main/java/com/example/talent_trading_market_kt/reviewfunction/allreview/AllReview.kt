package com.example.talent_trading_market_kt.reviewfunction.allreview

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.example.talent_trading_market_kt.reviewfunction.api.ReviewFunctionApi
import com.example.talent_trading_market_kt.reviewfunction.dto.ReviewReadResponse
import com.example.talent_trading_market_kt.reviewfunction.makereview.ReviewWrite
import kotlinx.android.synthetic.main.review_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllReview  : AppCompatActivity() {
    lateinit var write_review_bt:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_page)
        var Id:Long
        write_review_bt=findViewById(R.id.write_review)
        Id = intent.getStringExtra("postId").toString().toLong()


        write_review_bt.setOnClickListener {
            val intent= Intent(this@AllReview,ReviewWrite::class.java)
            intent.putExtra("postId",Id.toString())
            startActivity(intent)
        }

        val service = RetrofitConnection.getInstance().create(ReviewFunctionApi::class.java)
        if(service!=null)
        {
            service.getAllPostReview(Id).enqueue(object : Callback<List<ReviewReadResponse>> {
                override fun onResponse(call: Call<List<ReviewReadResponse>>, response: Response<List<ReviewReadResponse>>) {
                    if (response.isSuccessful) {
                        var reviewlist:List<ReviewReadResponse>;
                        reviewlist= response.body()!!;
                        reviews.layoutManager= LinearLayoutManager(this@AllReview,
                            LinearLayoutManager.VERTICAL,false)
                        reviews.setHasFixedSize(true)
                        reviews.adapter= AllReviewAdapter(reviewlist)
                    }
                }

                override fun onFailure(call: Call<List<ReviewReadResponse>?>, t: Throwable) {

                }

            })
        }
    }
}