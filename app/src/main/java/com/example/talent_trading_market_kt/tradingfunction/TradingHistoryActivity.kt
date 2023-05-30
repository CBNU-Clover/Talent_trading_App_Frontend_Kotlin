
package com.example.talent_trading_market_kt.tradingfunction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.BoardFunctionApi
import com.example.talent_trading_market_kt.response.PostGetAllBoard
import com.example.talent_trading_market_kt.response.trade.TradingHistory.TradingHistory
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.activity_myboardhistory.*
import kotlinx.android.synthetic.main.activity_tradehistory.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TradingHistoryActivity : AppCompatActivity() {
    lateinit var backbt_trading_history:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tradehistory)
        backbt_trading_history=findViewById(R.id.backbt_trading_history)
        backbt_trading_history.setOnClickListener {
            finish()
        }
        val service = RetrofitConnection.getInstance().create(TradingFunctionApi::class.java)
        if(service!=null)
        {
            service.trade_history().enqueue(object : Callback<List<TradingHistory>> {
                override fun onResponse(call: Call<List<TradingHistory>>, response: Response<List<TradingHistory>>) {
                    if (response.isSuccessful) {
                        var tradeList:List<TradingHistory>;
                        tradeList= response.body()!!;
                        Trading_History_view.layoutManager=LinearLayoutManager(this@TradingHistoryActivity, LinearLayoutManager.VERTICAL,false)
                        Trading_History_view.setHasFixedSize(true)
                        Trading_History_view.adapter=TradingHistoryAdapter(tradeList)
                    }
                }

                override fun onFailure(call: Call<List<TradingHistory>?>, t: Throwable) {
                    Toast.makeText(this@TradingHistoryActivity, "다시 버튼을 눌러주세요", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }

    }

}
