package com.example.talent_trading_market_kt.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.BoardFunctionApi
import com.example.talent_trading_market_kt.boardfunction.mainmenu.hottrade.HotTradeBoardAdapter
import com.example.talent_trading_market_kt.boardfunction.postsearch.SearchBoardAdapter
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostSearch
import com.example.talent_trading_market_kt.response.PostGetAllBoard
import com.example.talent_trading_market_kt.response.PostSearchResult
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_myboardhistory.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment1_Home:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.activity_main,container,false)
        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)
        val postSearch= PostSearch()
        if(service!=null)
        {
            service.postsearch(postSearch).enqueue(object : Callback<List<PostSearchResult>> {
                override fun onResponse(call: Call<List<PostSearchResult>>, response: Response<List<PostSearchResult>>) {
                    if (response.isSuccessful) {
                        var searchboardList:List<PostSearchResult>;
                        searchboardList= response.body()!!;
                        hottrade_view.layoutManager= LinearLayoutManager(requireContext(),
                            LinearLayoutManager.HORIZONTAL,false)
                        hottrade_view.setHasFixedSize(true)
                        hottrade_view.adapter= HotTradeBoardAdapter(searchboardList)
                    }
                }

                override fun onFailure(call: Call<List<PostSearchResult>?>, t: Throwable) {

                }

            })
        }


        return view
    }
}