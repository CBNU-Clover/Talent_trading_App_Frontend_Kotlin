package com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.dto.memberfunctiondto.MyProfile
import com.example.talent_trading_market_kt.memberfunction.api.MemberFunctionApi
import com.example.talent_trading_market_kt.pointfunction.point_history.MyPointActivity
import com.example.talent_trading_market_kt.retrofit.App
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.example.talent_trading_market_kt.tradingfunction.trading_history.TradingHistoryActivity
import kotlinx.android.synthetic.main.mypage.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyPageActivity : AppCompatActivity() {
    lateinit var point_bt:TextView
    lateinit var myboard:TextView
    lateinit var trading_history_bt:TextView
    lateinit var mypage_backbt: ImageButton
    lateinit var my_nickname:TextView
    lateinit var my_ranking:TextView
    lateinit var my_photo:ImageView
    lateinit var QA_bt:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage)
        point_bt=findViewById(R.id.mypoint_btn)
        myboard=findViewById(R.id.history_btn)
        trading_history_bt=findViewById(R.id.trading_history_bt)
        mypage_backbt=findViewById(R.id.mypage_backbt)
        my_nickname=findViewById(R.id.my_nickname)
        my_ranking=findViewById(R.id.my_ranking)
        my_photo=findViewById(R.id.myprofile)
        QA_bt=findViewById(R.id.QA_Button)

        val service = RetrofitConnection.getInstance().create(MemberFunctionApi::class.java)
        if (service != null) {

            service.getMyProfile().enqueue(object : Callback<MyProfile?> {
                override fun onResponse(
                    call: Call<MyProfile?>,
                    response: Response<MyProfile?>
                ) {
                    if (response.isSuccessful) {
                        var myprofile: MyProfile
                        myprofile = response.body()!!
                        my_nickname.text =myprofile.my_nickname
                        my_ranking.text=myprofile.my_ranking
                        Glide.with(this@MyPageActivity)
                            .load(App.prefs.image+myprofile.my_image_url.toString())
                            .dontAnimate()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(my_photo)
                    }
        }
                override fun onFailure(call: Call<MyProfile?>, t: Throwable) {

                }

            })
        }
        QA_bt.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSdE0RrrbvNMPGjRsmYNSQq27wFXUMtjaOnuvISsnp63XbB81g/viewform"))
            startActivity(intent)
        }
        //거래기록 버튼
        trading_history_bt.setOnClickListener {
            val intent = Intent(this, TradingHistoryActivity::class.java)
            startActivity(intent)
        }

        //내 글목록 버튼
        history_btn.setOnClickListener {
            val intent = Intent(this, ReadMyBoardActivity::class.java)
            startActivity(intent)
        }

        //포인트 버튼
        point_bt.setOnClickListener {
            val intent = Intent(this, MyPointActivity::class.java)
            startActivity(intent)
        }
        mypage_backbt.setOnClickListener {
            finish()
        }
    }
}


