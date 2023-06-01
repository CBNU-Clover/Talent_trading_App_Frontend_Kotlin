package com.example.talent_trading_market_kt.payfunction

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.dto.tradefunctiondto.TradePost
import com.example.talent_trading_market_kt.dto.tradefunctiondto.TradingFunctionApi
import com.example.talent_trading_market_kt.pointfunction.PointFunctionApi
import com.example.talent_trading_market_kt.response.pointresponse.ShowPointDTO
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.activity_trade.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PayMentActivity : AppCompatActivity() {
    lateinit var payment:Button
    lateinit var pay_title:TextView
    lateinit var current_point:TextView
    lateinit var final_point:TextView
    lateinit var pay_date:TextView
    lateinit var pay_content:TextView
    lateinit var backbt_payment:ImageButton
    var flag:Int=0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trade)
        payment=findViewById(R.id.payment)
        pay_title=findViewById(R.id.pay_title)
        current_point=findViewById(R.id.current_point)
        final_point=findViewById(R.id.final_point)
        pay_date=findViewById(R.id.pay_date)
        pay_content=findViewById(R.id.pay_content)
        backbt_payment=findViewById(R.id.backbt_payment)
        backbt_payment.setOnClickListener {
            finish()
        }
        if(intent.hasExtra("pay_content"))
        {
            pay_content.text=intent.getStringExtra("pay_content")

        }
        if(intent.hasExtra("pay_title"))
        {
            pay_title.text=intent.getStringExtra("pay_title")

        }
        if(intent.hasExtra("pay_price"))
        {

            board_first_price.text=intent.getStringExtra("pay_price")
            board_final_price.text=intent.getStringExtra("pay_price")


        }
        if(intent.hasExtra("pay_date"))
        {

            pay_date.text=intent.getStringExtra("pay_date")

        }
        val service = RetrofitConnection.getInstance().create(PointFunctionApi::class.java)
        if (service != null) {
            service.show_point().enqueue(object : Callback<ShowPointDTO> {
                override fun onResponse(call: Call<ShowPointDTO>, response: Response<ShowPointDTO>) {
                    if (response.isSuccessful) {
                        var result:Long
                        var showPointDTO= ShowPointDTO()
                        showPointDTO= response.body()!!
                        current_point.text= showPointDTO.point.toString()+"원"
                        val boardFinalPriceString = board_final_price.text.toString().replace("원", "")
                        val boardFinalPriceLong= boardFinalPriceString.toLongOrNull()
                        if (boardFinalPriceLong != null) {
                            result= showPointDTO.point!! - boardFinalPriceLong
                            final_point.text = result.toString() + "원"
                        } else {

                            // 문자열을 Long으로 변환할 수 없는 경우에 대한 처리
                        }
                        if(showPointDTO.point!!>= boardFinalPriceLong!!)
                        {
                            flag=1;
                        }
                    }
                }

                override fun onFailure(call: Call<ShowPointDTO>, t: Throwable) {
                }

            })
        }



        payment.setOnClickListener {
            if(flag==1)
            {
                val builder = AlertDialog.Builder(this)
                builder
                    .setTitle("알림")
                    .setMessage("거래를 진행하겠습니까?")
                    .setPositiveButton("진행",
                        DialogInterface.OnClickListener { dialog, id ->
                val service = RetrofitConnection.getInstance().create(TradingFunctionApi::class.java)
                var tradePost=TradePost()
                tradePost.tradePost_id= intent.getStringExtra("pay_Id")?.toLong()
                if (service != null) {
                    service.trade(tradePost).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@PayMentActivity, "거래 완료", Toast.LENGTH_SHORT).show()
                                finish()

                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
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
            else if(flag==0)
            {
                Toast.makeText(this@PayMentActivity, "잔액이 부족합니다", Toast.LENGTH_SHORT).show()
            }
            

            //결제가 이루어진다.
        }
    }
}