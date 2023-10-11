package com.example.talent_trading_market_kt.chatfunction.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.databinding.LeftBalloonItemBinding
import com.example.talent_trading_market_kt.databinding.RightBalloonItemBinding
import com.example.talent_trading_market_kt.databinding.SendBinding


class TalkAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val lst = mutableListOf<Talk>()
    val LEFT_TALK =0
    val RIGHT_TALK =1
    val TRADING_REQUEST=2
    private lateinit var leftBalloonBinding: LeftBalloonItemBinding
    private lateinit var rightBalloonBinding: RightBalloonItemBinding
    private lateinit var sendBinding:SendBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            LEFT_TALK->{
                leftBalloonBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    R.layout.left_balloon_item,parent,false)
                LeftViewHolder(leftBalloonBinding)
            }

            RIGHT_TALK->{
                rightBalloonBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.right_balloon_item,parent,false)
                RightViewHolder(rightBalloonBinding)
            }
            TRADING_REQUEST->{
                sendBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.send,parent,false)
                TradingRequestViewHolder(sendBinding)
            }
            else->{
                throw RuntimeException("알 수 없는 viewtype error")
            }
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is LeftViewHolder){
            holder.binding.leftTalk.text = lst[position].talkContent
            holder.binding.leftDate.text=lst[position].talkdate
        }
        else if (holder is RightViewHolder){
            holder.binding.rightTalk.text = lst[position].talkContent
            holder.binding.rightDate.text=lst[position].talkdate
        }
        else if (holder is TradingRequestViewHolder)
        {
            holder.binding.finalPriceRequest.text=lst[position].talkContent
            holder.binding.finalPriceRequestContent.text=lst[position].talkContent
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (lst[position].type == "left") {
            return LEFT_TALK
        } else if (lst[position].type == "right") {
            return RIGHT_TALK
        } else if (lst[position].type == "trading_request") {
            return TRADING_REQUEST
        } else {
            return super.getItemViewType(position) // 누락된 경우 기본값 반환
        }
    }



    inner class LeftViewHolder(val binding:LeftBalloonItemBinding)
        :RecyclerView.ViewHolder(binding.root){
    }
    inner class RightViewHolder(val binding:RightBalloonItemBinding)
        :RecyclerView.ViewHolder(binding.root){
    }
    inner class TradingRequestViewHolder(val binding:SendBinding)
        :RecyclerView.ViewHolder(binding.root){
    }

    fun addItem(talk: Talk){
        lst.add(talk)
    }
}