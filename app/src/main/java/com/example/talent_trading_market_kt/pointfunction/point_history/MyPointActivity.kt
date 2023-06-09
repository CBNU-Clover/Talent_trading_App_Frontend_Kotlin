package com.example.talent_trading_market_kt.pointfunction.point_history

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.pointfunction.api.PointFunctionApi
import com.example.talent_trading_market_kt.pointfunction.charge_point.ChargePointActivity
import com.example.talent_trading_market_kt.response.pointresponse.PointHistory
import com.example.talent_trading_market_kt.response.pointresponse.ShowPointDTO
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.point_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPointActivity  : AppCompatActivity() {
    lateinit var chargepoint:TextView
    lateinit var showpoint:TextView
    lateinit var back_btn_point:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.point_page)
        chargepoint=findViewById(R.id.charge_point)
        showpoint=findViewById(R.id.showpoint)
        back_btn_point=findViewById(R.id.back_btn_point)
        back_btn_point.setOnClickListener {
            finish()
        }
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
        if (service != null) {
            service.point_history().enqueue(object : Callback<List<PointHistory>> {
                override fun onResponse(call: Call<List<PointHistory>>, response: Response<List<PointHistory>>) {
                    if (response.isSuccessful) {
                        var point_history_List:List<PointHistory>;
                        point_history_List= response.body()!!;
                        my_point_history.layoutManager= LinearLayoutManager(this@MyPointActivity, LinearLayoutManager.VERTICAL,false)
                        my_point_history.setHasFixedSize(true)
                        my_point_history.adapter= PointHistoryAdapter(point_history_List)
                    }
                }

                override fun onFailure(call: Call<List<PointHistory>>, t: Throwable) {
                }

            })
        }

        chargepoint.setOnClickListener {
            val intent= Intent(this, ChargePointActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}