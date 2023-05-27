package com.example.talent_trading_market_kt.boardfunction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.talent_trading_market_kt.MainActivity
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostBoardDTO
import com.example.talent_trading_market_kt.fragment.Fragment2_Menu
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.activity_boardwrite.calendarView
import kotlinx.android.synthetic.main.activity_boardwrite.selectdate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MakeBoardActivity : AppCompatActivity() {
    lateinit var postName: EditText
    lateinit var content: EditText
    lateinit var write_bt:Button
    lateinit var selectdate: TextView
    lateinit var calendarView: CalendarView
    lateinit var makeboard_price:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boardwrite)
        postName = findViewById(R.id.title)
        content = findViewById(R.id.content)
        write_bt = findViewById(R.id.make_content)
        selectdate = findViewById(R.id.selectdate)
        calendarView = findViewById(R.id.calendarView)
        makeboard_price=findViewById(R.id.makeboard_price)

        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)

        fun openCalendar(view: View) {
            calendarView.visibility = View.VISIBLE

            // 캘린더뷰 선택 리스너 설정
            calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                // 선택한 날짜를 텍스트뷰에 설정
                selectdate.text = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth)

                // 캘린더뷰 숨김 처리
                calendarView.visibility = View.GONE
            }
        }

        write_bt.setOnClickListener {
            val postname=postName.text.toString()
            val content=content.text.toString()
            val price=makeboard_price.text.toString()
            val makeprice=price.toLong()
            val postBoardDTO= PostBoardDTO()
            postBoardDTO.writerNickname="writer"
            postBoardDTO.postName=postname
            postBoardDTO.content=content
            postBoardDTO.price=makeprice
            if(service!=null)
            {
                service.make_board(postBoardDTO).enqueue(object : Callback<Long?> {
                    override fun onResponse(call: Call<Long?>, response: Response<Long?>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@MakeBoardActivity, "게시물 쓰기 완료", Toast.LENGTH_SHORT).show()
                            val intent=Intent(this@MakeBoardActivity,MainActivity::class.java)
                            finishAffinity()
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<Long?>, t: Throwable) {
                        Toast.makeText(this@MakeBoardActivity, "다시 버튼을 눌러주세요", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
            }
        }
    }
}