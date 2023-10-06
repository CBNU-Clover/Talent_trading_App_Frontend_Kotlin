package com.example.talent_trading_market_kt.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction.MyPageActivity
import com.example.talent_trading_market_kt.boardfunction.postsearch.SearchBoardActivity
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostSearch
import com.example.talent_trading_market_kt.response.postresponse.PostSearchResult
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment3_Home:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.main_page,container,false)
        val mypage: ImageView =view.findViewById(R.id.mypage)
        val home_search_bt:ImageButton=view.findViewById(R.id.searchButton)
        val home_search:EditText=view.findViewById(R.id.home_search)
        mypage.setOnClickListener {
            val activity = requireActivity()
            val intent = Intent(activity, MyPageActivity::class.java)
            activity.startActivity(intent)
        }
        home_search_bt.setOnClickListener {
            val activity = requireActivity()
            val intent = Intent(activity, SearchBoardActivity::class.java)
            intent.putExtra("home_search",home_search.text.toString())
            activity.startActivity(intent)
        }


        return view
    }
}