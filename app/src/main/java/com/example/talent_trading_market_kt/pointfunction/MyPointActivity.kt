package com.example.talent_trading_market_kt.pointfunction

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.talent_trading_market_kt.MainActivity
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.fragment.Fragment3_MyPage
import com.example.talent_trading_market_kt.response.pointresponse.ShowPointDTO
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.activity_mypage.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPointActivity  : AppCompatActivity() {
    lateinit var chargepoint:TextView
    lateinit var showpoint:TextView
    lateinit var back_button_tomypage:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point)
        chargepoint=findViewById(R.id.charge_point)
        showpoint=findViewById(R.id.showpoint)
        back_button_tomypage=findViewById(R.id.back_button_tomypage)
        val service = RetrofitConnection.getInstance().create(PointFunctionApi::class.java)
        if (service != null) {
            service.show_point().enqueue(object : Callback<ShowPointDTO> {
                override fun onResponse(call: Call<ShowPointDTO>, response: Response<ShowPointDTO>) {
                    if (response.isSuccessful) {
                        var showPointDTO=ShowPointDTO()
                        showPointDTO= response.body()!!
                        showpoint.text= showPointDTO.point.toString()+" 포인트"
                    }
                }

                override fun onFailure(call: Call<ShowPointDTO>, t: Throwable) {
                }

            })
        }
        back_button_tomypage.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        chargepoint.setOnClickListener {
            val intent= Intent(this,ChargePointActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}