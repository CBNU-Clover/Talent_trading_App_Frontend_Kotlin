package com.example.talent_trading_market_kt.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.ranking.RankingAdapter
import com.example.talent_trading_market_kt.ranking.api.RankingFunctionApi
import com.example.talent_trading_market_kt.ranking.dto.MyRanking
import com.example.talent_trading_market_kt.ranking.dto.ResponseRankingList
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.ranking_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment1_Ranking:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.ranking_page,container,false)
        val service = RetrofitConnection.getInstance().create(RankingFunctionApi::class.java)
        val myrank_nickname: TextView =view.findViewById(R.id.myranking_nickname)
        val myrank_rating: TextView =view.findViewById(R.id.myranking_rating)
        val myrank_grade: TextView =view.findViewById(R.id.myranking_grade)


        if(service!=null)
        {
            service.Ranking().enqueue(object : Callback<List<ResponseRankingList>> {
                override fun onResponse(call: Call<List<ResponseRankingList>>, response: Response<List<ResponseRankingList>>) {
                    if (response.isSuccessful) {
                        var rankingList:List<ResponseRankingList>;
                        rankingList= response.body()!!;
                        show_ranking.layoutManager= LinearLayoutManager(requireContext(),
                            LinearLayoutManager.VERTICAL,false)
                        show_ranking.setHasFixedSize(true)
                        show_ranking.adapter= RankingAdapter(rankingList)

                    }
                }

                override fun onFailure(call: Call<List<ResponseRankingList>?>, t: Throwable) {

                }

            })
        }
        if(service!=null)
        {
            service.myRanking().enqueue(object : Callback<MyRanking> {
                override fun onResponse(call: Call<MyRanking>, response: Response<MyRanking>) {
                    if (response.isSuccessful) {
                        var myranking:MyRanking;
                        myranking= response.body()!!
                        myrank_nickname.text=myranking.nickname.toString()
                        myrank_rating.text=myranking.score.toString()
                        myrank_grade.text=myranking.grade.toString()
                    }
                }

                override fun onFailure(call: Call<MyRanking?>, t: Throwable) {

                }

            })
        }

        return view
    }
}