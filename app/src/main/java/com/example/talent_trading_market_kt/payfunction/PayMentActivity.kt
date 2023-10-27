package com.example.talent_trading_market_kt.payfunction


import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.boardfunction.postsearch.SearchOneBoardActivity
import com.example.talent_trading_market_kt.chatfunction.chat.ChatActivity
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostReadResponse
import com.example.talent_trading_market_kt.pointfunction.api.PointFunctionApi
import com.example.talent_trading_market_kt.response.pointresponse.ShowPointDTO
import com.example.talent_trading_market_kt.retrofit.App
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.example.talent_trading_market_kt.reviewfunction.api.ReviewFunctionApi
import com.example.talent_trading_market_kt.tradingfunction.api.TradingFunctionApi
import com.example.talent_trading_market_kt.tradingfunction.dto.TradePost
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
    lateinit var payment_bt: Button
    lateinit var pay_post_profile:ImageView
    lateinit var pay_writer_profile:ImageView
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
        payment_bt=findViewById(R.id.payment)
        pay_post_profile=findViewById(R.id.pay_post_profile)
        pay_writer_profile=findViewById(R.id.pay_writer_profile)

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
                        paypost_money.text = post.price.toString()+"원"
                        pay_money.text = post.price.toString() + "원"
                        paytotal_money.text = post.price.toString() + "원"
                        payfinal_money.text = post.price.toString() + "원"
                        Glide.with(this@PayMentActivity)
                            .load(App.prefs.image+post.writer_image_url.toString())
                            .dontAnimate()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .into(pay_writer_profile)
                        Glide.with(this@PayMentActivity)
                            .load(App.prefs.image+post.writer_image_url.toString())
                            .dontAnimate()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .into(pay_post_profile)
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
        val point_service = RetrofitConnection.getInstance().create(PointFunctionApi::class.java)
        if (point_service != null) {
            point_service.show_point().enqueue(object : Callback<ShowPointDTO> {
                override fun onResponse(call: Call<ShowPointDTO>, response: Response<ShowPointDTO>) {
                    if (response.isSuccessful) {
                        var showPointDTO= ShowPointDTO()
                        showPointDTO= response.body()!!
                        App.prefs.point=showPointDTO.point.toString()
                    }
                }

                override fun onFailure(call: Call<ShowPointDTO>, t: Throwable) {
                }

            })
        }
        payment_bt.setOnClickListener {
            var price=paypost_money.text.toString().replace("원","")
            if(App.prefs.point.toString().toLong()>=price.toLong())
            {
                val builder = AlertDialog.Builder(this)
                builder
                    .setTitle("알림")
                    .setMessage("정말 결제를 진행하시겠습니까?")
                    .setPositiveButton("진행",
                        DialogInterface.OnClickListener { dialog, id ->
                            val tradeservice=RetrofitConnection.getInstance().create(TradingFunctionApi::class.java)
                            if(tradeservice!=null)
                            {
                                val tradePost=TradePost()
                                tradePost.tradePost_id=postId
                                tradeservice.trade(tradePost).enqueue(object : Callback<Void> {
                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                        if (response.isSuccessful) {
                                            Toast.makeText(this@PayMentActivity, "거래 성공", Toast.LENGTH_SHORT).show()
                                            App.prefs.trade_status_complete="success"
                                            finish()
                                        }
                                    }

                                    override fun onFailure(call: Call<Void?>, t: Throwable) {

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
            else if(App.prefs.point.toString().toLong()<price.toLong())
            {
                Toast.makeText(this@PayMentActivity, "잔액이 부족합니다!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 버튼을 누르면 알람이 뜨면서 결제 진행 여부를 물어본다.
    // 결제가 완료되면 자동으로 거래기록과 포인트 기록이 추가되면서
    // 채팅방에 거래완료 표시가 뜬다.
}