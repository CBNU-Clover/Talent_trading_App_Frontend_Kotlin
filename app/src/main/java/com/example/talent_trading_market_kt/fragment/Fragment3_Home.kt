package com.example.talent_trading_market_kt.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction.MyPageActivity
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
        val postSearch= PostSearch()
        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)
        mypage.setOnClickListener {
            val activity = requireActivity()
            val intent = Intent(activity, MyPageActivity::class.java)
            activity.startActivity(intent)
        }
        if(service!=null)
        {
            service.postsearch(postSearch).enqueue(object : Callback<List<PostSearchResult>> {
                override fun onResponse(call: Call<List<PostSearchResult>>, response: Response<List<PostSearchResult>>) {
                    if (response.isSuccessful) {
                        /*var searchboardList:List<PostSearchResult>;
                        searchboardList= response.body()!!;
                        hottrade_view.layoutManager= LinearLayoutManager(requireContext(),
                            LinearLayoutManager.HORIZONTAL,false)
                        hottrade_view.setHasFixedSize(true)
                        hottrade_view.adapter= HotTradeBoardAdapter(searchboardList)
                        realtime_view.layoutManager= LinearLayoutManager(requireContext(),
                            LinearLayoutManager.HORIZONTAL,false)
                        realtime_view.setHasFixedSize(true)
                        realtime_view.adapter= HotTradeBoardAdapter(searchboardList)*/
                    }
                }

                override fun onFailure(call: Call<List<PostSearchResult>?>, t: Throwable) {

                }

            })
        }


        return view
    }
}