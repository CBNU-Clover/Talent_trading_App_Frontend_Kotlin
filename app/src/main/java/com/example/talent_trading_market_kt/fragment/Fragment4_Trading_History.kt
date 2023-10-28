package com.example.talent_trading_market_kt.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.response.trade.TradingHistory.TradingHistory
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.example.talent_trading_market_kt.tradingfunction.api.TradingFunctionApi
import com.example.talent_trading_market_kt.tradingfunction.trading_history.TradingHistoryAdapter
import kotlinx.android.synthetic.main.mytrading_history_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment4_Trading_History:Fragment() {
    override fun onResume() {
        super.onResume()
        updateFragmentData()
    }

    private fun updateFragmentData() {
        val service = RetrofitConnection.getInstance().create(TradingFunctionApi::class.java)
        if(service!=null)
        {
            service.trade_history().enqueue(object : Callback<List<TradingHistory>> {
                override fun onResponse(call: Call<List<TradingHistory>>, response: Response<List<TradingHistory>>) {
                    if (response.isSuccessful) {
                        var tradeList:List<TradingHistory>;
                        tradeList= response.body()!!;
                        Trading_History_view2.layoutManager=LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                        Trading_History_view2.setHasFixedSize(true)
                        Trading_History_view2.adapter= TradingHistoryAdapter(tradeList)
                    }
                }

                override fun onFailure(call: Call<List<TradingHistory>?>, t: Throwable) {
                    Toast.makeText(requireContext(), "다시 버튼을 눌러주세요", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.mytrading_history_fragment,container,false)
        return view
    }
}