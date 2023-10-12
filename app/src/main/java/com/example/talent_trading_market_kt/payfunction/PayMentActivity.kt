package com.example.talent_trading_market_kt.payfunction


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.boardfunction.postsearch.SearchOneBoardActivity
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostReadResponse
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.example.talent_trading_market_kt.reviewfunction.api.ReviewFunctionApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PayMentActivity : AppCompatActivity() {
    lateinit var paypost_name: TextView
    lateinit var paypost_money: TextView
    lateinit var pay_seller: TextView
    lateinit var seller_star: TextView
    lateinit var pay_money: TextView
    lateinit var paytotal_money: TextView
    lateinit var payfinal_money: TextView
    lateinit var backbt_pay:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_page)
        var postId: Long
        postId = intent.getStringExtra("postId").toString().toLong()
        paypost_name = findViewById(R.id.paypost_name)
        paypost_money = findViewById(R.id.paypost_money)
        pay_seller = findViewById(R.id.pay_seller)
        seller_star = findViewById(R.id.seller_star)
        pay_money = findViewById(R.id.pay_money)
        paytotal_money = findViewById(R.id.paytotal_money)
        payfinal_money = findViewById(R.id.payfinal_money)
        backbt_pay=findViewById(R.id.backbt_payment)

        backbt_pay.setOnClickListener {
            finish()
        }
        paypost_name.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val intent= Intent(this@PayMentActivity, SearchOneBoardActivity::class.java)
                intent.putExtra("Search_Id",postId.toString())
                startActivity(intent)

            }
        })
        val boardservice = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)
        if (boardservice != null) {
            boardservice.readPost(postId).enqueue(object : Callback<PostReadResponse> {
                override fun onResponse(
                    call: Call<PostReadResponse>,
                    response: Response<PostReadResponse>
                ) {
                    if (response.isSuccessful) {
                        var post: PostReadResponse
                        post = response.body()!!
                        pay_seller.text = post.writerNickname
                        paypost_name.text = post.postName
                        paypost_money.text = post.price.toString()
                        pay_money.text = post.price.toString() + "원"
                        paytotal_money.text = post.price.toString() + "원"
                        payfinal_money.text = post.price.toString() + "원"
                    }
                }

                override fun onFailure(call: Call<PostReadResponse?>, t: Throwable) {

                }

            })
        }
        val service = RetrofitConnection.getInstance().create(ReviewFunctionApi::class.java)
        if (service != null) {
            service.getPostAvg(postId).enqueue(object : Callback<Double> {
                override fun onResponse(call: Call<Double>, response: Response<Double>) {
                    if (response.isSuccessful) {
                        var rating_av: Double
                        rating_av = response.body()!!
                        val formattedRating = String.format("%.1f", rating_av)
                        seller_star.text = formattedRating
                    }
                }

                override fun onFailure(call: Call<Double?>, t: Throwable) {

                }

            })

        }
    }
}