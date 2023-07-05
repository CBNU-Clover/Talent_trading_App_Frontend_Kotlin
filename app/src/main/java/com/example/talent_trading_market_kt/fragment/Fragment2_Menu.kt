package com.example.talent_trading_market_kt.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.boardfunction.board_page.makeboard_page.MakeBoardActivity
import com.example.talent_trading_market_kt.boardfunction.postsearch.SearchBoardActivity
import com.example.talent_trading_market_kt.boardfunction.postsearch.SearchBoardAdapter
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostSearch
import com.example.talent_trading_market_kt.response.postresponse.PostSearchResult
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.allboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment2_Menu:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.allboard,container,false)
        val boardwrite:ImageButton=view.findViewById(R.id.write_button)
        val search_bt:ImageButton=view.findViewById(R.id.search_button)
        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)
        boardwrite.setOnClickListener {
            val activity = requireActivity()
            val intent = Intent(activity, MakeBoardActivity::class.java)
            activity.startActivity(intent)
        }
        search_bt.setOnClickListener {
            val activity = requireActivity()
            val intent = Intent(activity, SearchBoardActivity::class.java)
            activity.startActivity(intent)
        }
        val postSearch= PostSearch()
        if(service!=null)
        {
            service.postsearch(postSearch).enqueue(object : Callback<List<PostSearchResult>> {
                override fun onResponse(call: Call<List<PostSearchResult>>, response: Response<List<PostSearchResult>>) {
                    if (response.isSuccessful) {
                        var searchboardList:List<PostSearchResult>;
                        searchboardList= response.body()!!;
                        boards.layoutManager= LinearLayoutManager(requireContext(),
                            LinearLayoutManager.VERTICAL,false)
                        boards.setHasFixedSize(true)
                        boards.adapter= SearchBoardAdapter(searchboardList)
                    }
                }

                override fun onFailure(call: Call<List<PostSearchResult>?>, t: Throwable) {

                }

            })
        }
        
        return view
    }
}