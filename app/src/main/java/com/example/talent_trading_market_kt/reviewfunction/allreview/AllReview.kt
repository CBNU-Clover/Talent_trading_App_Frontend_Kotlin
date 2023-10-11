package com.example.talent_trading_market_kt.reviewfunction.allreview

import android.content.Intent
import android.media.Rating
import android.os.Bundle
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction.ReadMyBoardActivity
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostReadResponse
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.example.talent_trading_market_kt.reviewfunction.api.ReviewFunctionApi
import com.example.talent_trading_market_kt.reviewfunction.dto.ReviewReadResponse
import com.example.talent_trading_market_kt.reviewfunction.makereview.ReviewWrite
import kotlinx.android.synthetic.main.review_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllReview  : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        updateReview()
    }
    private fun updateReview()
    {
        var Id:Long
        Id = intent.getStringExtra("postId").toString().toLong()
        val service = RetrofitConnection.getInstance().create(ReviewFunctionApi::class.java)
        if(service!=null)
        {
            service.getAllPostReview(Id).enqueue(object : Callback<List<ReviewReadResponse>> {
                override fun onResponse(call: Call<List<ReviewReadResponse>>, response: Response<List<ReviewReadResponse>>) {
                    if (response.isSuccessful) {
                        var reviewlist:List<ReviewReadResponse>;
                        reviewlist= response.body()!!;
                        Allreview_size.text="구매 후기 "+reviewlist.size.toString()+"개"
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
        if(service!=null)
        {
            service.getPostAvg(Id).enqueue(object : Callback<Double> {
                override fun onResponse(call: Call<Double>, response: Response<Double>) {
                    if (response.isSuccessful) {
                        var rating_av :Double
                        rating_av=response.body()!!
                        val formattedRating=String.format("%.1f",rating_av)
                        Allreview_score.text=formattedRating.toFloat().toString()
                        review_rating_av.rating=rating_av.toFloat()
                    }
                }

                override fun onFailure(call: Call<Double?>, t: Throwable) {

                }

            })
        }
    }
    lateinit var write_review_bt:TextView
    lateinit var Allreview_score:TextView
    lateinit var review_rating_av: RatingBar
    lateinit var Allreview_size:TextView
    lateinit var rv_title:TextView
    lateinit var rv_price:TextView
    lateinit var rv_backbt:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_page)
        var Id:Long
        write_review_bt=findViewById(R.id.write_review)
        Allreview_score=findViewById(R.id.Allreview_score)
        review_rating_av=findViewById(R.id.review_rating_av)
        Allreview_size=findViewById(R.id.Allreview_size)
        rv_title=findViewById(R.id.rv_title)
        rv_price=findViewById(R.id.rv_price)
        rv_backbt=findViewById(R.id.rv_backbt)
        Id = intent.getStringExtra("postId").toString().toLong()

        rv_backbt.setOnClickListener {
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
                        rv_title.text=post.postName
                        rv_price.text=post.price.toString()+"원"
                    }
                }

                override fun onFailure(call: Call<PostReadResponse?>, t: Throwable) {

                }

            })
        }

        write_review_bt.setOnClickListener {
            val intent= Intent(this@AllReview,ReviewWrite::class.java)
            intent.putExtra("postId",Id.toString())
            startActivity(intent)
        }
    }
}