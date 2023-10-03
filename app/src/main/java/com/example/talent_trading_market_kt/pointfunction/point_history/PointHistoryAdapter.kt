package com.example.talent_trading_market_kt.pointfunction.point_history

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.response.pointresponse.PointHistory

class PointHistoryAdapter(var my_point_history: List<PointHistory>): RecyclerView.Adapter<PointHistoryAdapter.CustomViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_point_history,parent,false)
        // parent ( 리사이클뷰를 적용할 activity ) 와 boarlist_item xml 화면을 붙인다(inflate)
        return CustomViewHolder(view)
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //실질적으로 연결해주는 부분 // 스크롤 내릴때 지속적으로 호출이 되는 곳
        holder.trade_point_postname.text=my_point_history.get(position).recipient
        if(my_point_history.get(position).status=="입금")
        {
            holder.point_status.text=my_point_history.get(position).status
            holder.point_status.setTextColor(Color.parseColor("#3DC181"));
            holder.trade_point_amount.text= "+"+my_point_history.get(position).amount.toString()+"원"
            holder.trade_point_amount.setTextColor(Color.parseColor("#3DC181"))
        }
        else
        {
            holder.point_status.text=my_point_history.get(position).status
            holder.point_status.setTextColor(Color.BLUE)
            holder.trade_point_amount.text= "-"+my_point_history.get(position).amount.toString()+"원"
            holder.trade_point_amount.setTextColor(Color.BLACK)
        }
        holder.point_balance.text=my_point_history.get(position).balance.toString()+"원"
        holder.point_time.text=my_point_history.get(position).date

    }

    override fun getItemCount(): Int {
        return my_point_history.size
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val trade_point_postname=itemView.findViewById<TextView>(R.id.trade_point_postname) // sender
        val point_status=itemView.findViewById<TextView>(R.id.point_status) //입금 or 출금
        val trade_point_amount=itemView.findViewById<TextView>(R.id.trade_point_amount)//가격
        val point_balance=itemView.findViewById<TextView>(R.id.point_balance) // 잔액
        val point_time=itemView.findViewById<TextView>(R.id.point_time)// 작성일자
    }

}