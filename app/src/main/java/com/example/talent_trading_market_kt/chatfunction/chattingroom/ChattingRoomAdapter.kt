
package com.example.talent_trading_market_kt.chatfunction.chattingroom


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.chatfunction.chat.ChatActivity
import com.example.talent_trading_market_kt.chatfunction.response.ChattingRoomListDTO

class ChattingRoomAdapter(val chattingRoomList: List<ChattingRoomListDTO>): RecyclerView.Adapter<ChattingRoomAdapter.CustomViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChattingRoomAdapter.CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.chat_list,parent,false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                val chattingRoom:ChattingRoomListDTO=chattingRoomList.get(curPos)
                val intent= Intent(parent.context,ChatActivity::class.java)
                intent.putExtra("roomId",chattingRoom.roomId.toString())
                intent.putExtra("seller",chattingRoom.seller)
                parent.context.startActivity(intent)

            }
        }
    }

    override fun onBindViewHolder(holder: ChattingRoomAdapter.CustomViewHolder, position: Int) {
        holder.chat_seller.text=chattingRoomList.get(position).seller
    }

    override fun getItemCount(): Int {
        return chattingRoomList.size
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val chat_seller=itemView.findViewById<TextView>(R.id.chat_seller)
    }

}
