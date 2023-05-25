package com.example.talent_trading_market_kt.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction.ReadBoardActivity
import com.example.talent_trading_market_kt.pointfunction.MyPointActivity
import com.example.talent_trading_market_kt.pointfunction.PointFunctionApi
import com.example.talent_trading_market_kt.response.pointresponse.ShowPointDTO
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.activity_mypage.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment3_MyPage:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View=inflater.inflate(R.layout.activity_mypage,container,false)
        val myboard:ImageButton=view.findViewById(R.id.history_btn)
        val mypoint:ImageButton=view.findViewById(R.id.point_bt)
        val show_point_mypage:TextView=view.findViewById(R.id.show_mypage_point)
        mypoint.setOnClickListener {
                val activity = requireActivity()
                val intent = Intent(activity, MyPointActivity::class.java)
                activity.startActivity(intent)
            }

        myboard.setOnClickListener {
            val activity = requireActivity()
            val intent = Intent(activity, ReadBoardActivity::class.java)
            activity.startActivity(intent)
        }
        val service = RetrofitConnection.getInstance().create(PointFunctionApi::class.java)
        if (service != null) {
            service.show_point().enqueue(object : Callback<ShowPointDTO> {
                override fun onResponse(call: Call<ShowPointDTO>, response: Response<ShowPointDTO>) {
                    if (response.isSuccessful) {
                        var showPointDTO=ShowPointDTO()
                        showPointDTO= response.body()!!
                        show_mypage_point.text= showPointDTO.point.toString()+" 포인트"
                        nickname_mypage.text=showPointDTO.nickname
                    }
                }

                override fun onFailure(call: Call<ShowPointDTO>, t: Throwable) {
                }

            })
        }
        return view
    }
}