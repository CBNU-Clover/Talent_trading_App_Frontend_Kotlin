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
import com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction.ReadMyBoardActivity
import com.example.talent_trading_market_kt.pointfunction.point_history.MyPointActivity
import com.example.talent_trading_market_kt.pointfunction.api.PointFunctionApi
import com.example.talent_trading_market_kt.response.pointresponse.ShowPointDTO
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.example.talent_trading_market_kt.tradingfunction.trading_history.TradingHistoryActivity
import kotlinx.android.synthetic.main.mypage.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment5_Chatting:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View=inflater.inflate(R.layout.mypage,container,false)
        val myboard:ImageButton=view.findViewById(R.id.history_btn)
        val mypoint_btn:ImageButton=view.findViewById(R.id.mypoint_btn)
        val show_point_mypage:TextView=view.findViewById(R.id.show_mypage_point)
        val trading_history_bt:ImageButton=view.findViewById(R.id.trading_history_bt)
        mypoint_btn.setOnClickListener {
                val activity = requireActivity()
                val intent = Intent(activity, MyPointActivity::class.java)
                activity.startActivity(intent)
            }
        trading_history_bt.setOnClickListener {
            val activity = requireActivity()
            val intent = Intent(activity, TradingHistoryActivity::class.java)
            activity.startActivity(intent)
        }

        myboard.setOnClickListener {
            val activity = requireActivity()
            val intent = Intent(activity, ReadMyBoardActivity::class.java)
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