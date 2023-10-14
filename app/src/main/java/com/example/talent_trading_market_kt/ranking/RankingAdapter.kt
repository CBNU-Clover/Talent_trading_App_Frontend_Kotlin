
package com.example.talent_trading_market_kt.ranking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.ranking.dto.ResponseRankingList

class RankingAdapter(var rankingList: List<ResponseRankingList>): RecyclerView.Adapter<RankingAdapter.CustomViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingAdapter.CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_ranking,parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankingAdapter.CustomViewHolder, position: Int) {
        //실질적으로 연결해주는 부분 // 스크롤 내릴때 지속적으로 호출이 되는 곳
        holder.ranking_nickname.text=rankingList.get(position).nickname
        holder.ranking_rating.text=rankingList.get(position).score.toString()
        holder.ranking_grade.text=rankingList.get(position).rank
    }

    override fun getItemCount(): Int {
        return rankingList.size
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val ranking_nickname=itemView.findViewById<TextView>(R.id.nickname_ranking)
        val ranking_rating=itemView.findViewById<TextView>(R.id.ranking_rating)
        val ranking_grade=itemView.findViewById<TextView>(R.id.ranking_top)
    }

}
