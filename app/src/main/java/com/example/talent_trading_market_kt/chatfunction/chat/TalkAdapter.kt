package com.example.talent_trading_market_kt.chatfunction.chat

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.databinding.*
import com.example.talent_trading_market_kt.payfunction.PayMentActivity
import com.example.talent_trading_market_kt.tradingfunction.trading_history.TradingHistoryActivity


class TalkAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val lst = mutableListOf<Talk>()
    val LEFT_TALK =0
    val RIGHT_TALK =1
    val TRADING_REQUEST_RIGHT=2
    val TRADING_REQUEST_LEFT=3
    val TRADING_ACCEPT_RIGHT=4
    val TRADING_ACCEPT_LEFT=5
    private lateinit var leftBalloonBinding: LeftBalloonItemBinding
    private lateinit var rightBalloonBinding: RightBalloonItemBinding
    private lateinit var sendRightBinding: SendRightBinding
    private lateinit var sendLeftBinding: SendLeftBinding
    private lateinit var tradecompleteRightBinding: TradecompleteRightBinding
    private lateinit var tradecompleteLeftBinding: TradecompleteLeftBinding

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
            TRADING_REQUEST_RIGHT->{
                sendRightBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.send_right,parent,false)
                TradingRequestRightViewHolder(sendRightBinding)
            }
            TRADING_REQUEST_LEFT->{
                sendLeftBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.send_left,parent,false)
                TradingRequestLeftViewHolder(sendLeftBinding)
            }
            TRADING_ACCEPT_RIGHT->{
               tradecompleteRightBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.tradecomplete_right,parent,false)
                TradingCompleteRightViewHolder(tradecompleteRightBinding)
            }
            TRADING_ACCEPT_LEFT->{
                tradecompleteLeftBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.tradecomplete_left,parent,false)
                TradingCompleteLeftViewHolder(tradecompleteLeftBinding)
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
        else if (holder is TradingRequestRightViewHolder)
        {
            holder.binding.rightFinalPriceRequestContent.text=lst[position].talkContent+"을 보내주세요!"
            holder.binding.sendRightDate.text=lst[position].talkdate
        }
        else if( holder is TradingRequestLeftViewHolder)
        {
            holder.binding.leftFinalPriceRequest.text=lst[position].talkContent+" 송금요청"
            holder.binding.leftFinalPriceRequestContent.text= lst[position].sender+"님이 "+lst[position].talkContent+"을 \n송금 요청했어요."
            holder.binding.sendLeftDate.text=lst[position].talkdate
        }
        else if ( holder is TradingCompleteRightViewHolder)
        {
            holder.binding.tradecompleteRightPrice.text=lst[position].talkContent+" 거래 완료!!"
            holder.binding.tradecompleteRightDate.text=lst[position].talkdate
        }
        else if ( holder is TradingCompleteLeftViewHolder)
        {
            holder.binding.tradecompleteLeftPrice.text=lst[position].sender+"님이 "+lst[position].talkContent+" \n거래 완료했습니다!!"
            holder.binding.tradecompleteLeftDate.text=lst[position].talkdate
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (lst[position].type == "left") {
            return LEFT_TALK
        } else if (lst[position].type == "right") {
            return RIGHT_TALK
        } else if (lst[position].type == "trading_request_right") {
            return TRADING_REQUEST_RIGHT
        } else if (lst[position].type == "trading_request_left") {
            return TRADING_REQUEST_LEFT
        }
        else if (lst[position].type == "trading_accept_right") {
            return TRADING_ACCEPT_RIGHT
        }
        else if (lst[position].type == "trading_accept_left") {
            return TRADING_ACCEPT_LEFT
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

    inner class TradingRequestRightViewHolder(val binding:SendRightBinding)
        :RecyclerView.ViewHolder(binding.root){

    }
    inner class TradingRequestLeftViewHolder(val binding:SendLeftBinding)
        :RecyclerView.ViewHolder(binding.root){
        init {
            binding.goPaymentLeft.setOnClickListener {

                val intent= Intent(binding.root.context, PayMentActivity::class.java)
                intent.putExtra("postId", lst[adapterPosition].postId.toString())
                binding.root.context.startActivity(intent)
            }
        }
    }
    inner class TradingCompleteRightViewHolder(val binding:TradecompleteRightBinding)
        :RecyclerView.ViewHolder(binding.root){
        init {
            binding.showTradingRight.setOnClickListener {

                val intent= Intent(binding.root.context, TradingHistoryActivity::class.java)
                binding.root.context.startActivity(intent)
            }
        }

    }
    inner class TradingCompleteLeftViewHolder(val binding:TradecompleteLeftBinding)
        :RecyclerView.ViewHolder(binding.root){
        init {
            binding.showTradingLeft.setOnClickListener {

                val intent= Intent(binding.root.context, TradingHistoryActivity::class.java)
                binding.root.context.startActivity(intent)
            }
        }

    }

    fun addItem(talk: Talk){
        lst.add(talk)
    }
}