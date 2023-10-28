package com.example.talent_trading_market_kt.tradingfunction.trading_history


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.response.trade.TradingHistory.TradingHistory
import com.example.talent_trading_market_kt.retrofit.App

class TradingHistoryAdapter(var tradeList: List<TradingHistory>): RecyclerView.Adapter<TradingHistoryAdapter.CustomViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_trading_history,parent,false)
        return CustomViewHolder(view)
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //실질적으로 연결해주는 부분 // 스크롤 내릴때 지속적으로 호출이 되는 곳
        holder.trade_postname.text=tradeList.get(position).trade_postname
        holder.trade_item_price.text=tradeList.get(position).trade_price.toString()+"원"
        holder.trade_time.text=tradeList.get(position).date
        Glide.with(holder.itemView.context)
            .load(App.prefs.image+tradeList.get(position).trading_image_url.toString())
            .into(holder.trading_photo)
    }

    override fun getItemCount(): Int {
        return tradeList.size
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val trade_postname=itemView.findViewById<TextView>(R.id.tradetitle_item) // 제목
        val trade_item_price=itemView.findViewById<TextView>(R.id.item_price) //  가격
        val trade_time=itemView.findViewById<TextView>(R.id.trade_time)
        val trading_photo=itemView.findViewById<ImageView>(R.id.trading_history_photo)
    }

}