package com.example.talent_trading_market_kt.tradingfunction


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.response.trade.TradingHistory.TradingHistory

class TradingHistoryAdapter(var tradeList: List<TradingHistory>): RecyclerView.Adapter<TradingHistoryAdapter.CustomViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.trade_history,parent,false)
        return CustomViewHolder(view)
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //실질적으로 연결해주는 부분 // 스크롤 내릴때 지속적으로 호출이 되는 곳
        holder.trade_postname.text=tradeList.get(position).trade_postname
        holder.trade_item_price.text=tradeList.get(position).trade_price.toString()+"원"
        holder.trade_buyer.text= tradeList.get(position).buyer_nickname
        holder.trade_seller.text= tradeList.get(position).seller_nickname
        holder.trade_time.text=tradeList.get(position).date
    }

    override fun getItemCount(): Int {
        return tradeList.size
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val trade_postname=itemView.findViewById<TextView>(R.id.tradetitle_item) // 제목
        val trade_item_price=itemView.findViewById<TextView>(R.id.item_price) //  가격
        val trade_buyer=itemView.findViewById<TextView>(R.id.buyer)
        val trade_seller=itemView.findViewById<TextView>(R.id.seller)
        val trade_time=itemView.findViewById<TextView>(R.id.trade_time)
    }

}