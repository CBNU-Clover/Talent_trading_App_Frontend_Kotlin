package com.example.talent_trading_market_kt.fragment

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.MakeBoardActivity
import com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction.ReadBoardActivity

class Fragment2_Menu:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.activity_board,container,false)
        val boardwrite:ImageButton=view.findViewById(R.id.write_button)
        boardwrite.setOnClickListener {
            val activity = requireActivity()
            val intent = Intent(activity, MakeBoardActivity::class.java)
            activity.startActivity(intent)
        }

        return view
    }
}