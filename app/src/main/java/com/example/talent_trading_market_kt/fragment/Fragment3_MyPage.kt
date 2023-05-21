package com.example.talent_trading_market_kt.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction.ReadBoardActivity

class Fragment3_MyPage:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View=inflater.inflate(R.layout.activity_mypage,container,false)
        val myboard:ImageButton=view.findViewById(R.id.history_btn)
        myboard.setOnClickListener {
            val activity = requireActivity()
            val intent = Intent(activity, ReadBoardActivity::class.java)
            activity.startActivity(intent)
        }
        return view
    }
}