
package com.example.talent_trading_market_kt.chatfunction.chattingroom


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.chatfunction.chat.ChatActivity
import com.example.talent_trading_market_kt.chatfunction.response.ChattingRoomListDTO
import com.example.talent_trading_market_kt.retrofit.App

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
                if(chattingRoom.seller.toString().trim()!=App.prefs.nickname.toString().trim())
                {
                    intent.putExtra("seller",chattingRoom.seller.toString().trim())
                }
                else
                {
                    intent.putExtra("seller",chattingRoom.buyer.toString().trim())
                }
                intent.putExtra("board_name",chattingRoom.postname)
                intent.putExtra("board_price",chattingRoom.post_price+"원")
                intent.putExtra("postId",chattingRoom.postId.toString())
                intent.putExtra("chat_post_profile",chattingRoom.post_image_url.toString())
                parent.context.startActivity(intent)

            }
        }
    }

    override fun onBindViewHolder(holder: ChattingRoomAdapter.CustomViewHolder, position: Int) {
        if(chattingRoomList.get(position).seller==App.prefs.nickname)
        {
            holder.chat_otherperson.text=chattingRoomList.get(position).buyer
        }
        else
        {
            holder.chat_otherperson.text=chattingRoomList.get(position).seller
        }
        holder.chat_post_title.text=chattingRoomList.get(position).postname
        Glide.with(holder.itemView.context)
            .load(App.prefs.image+chattingRoomList.get(position).post_image_url.toString())
            .into(holder.chat_post_image)
        Glide.with(holder.itemView.context)
            .load(App.prefs.image+chattingRoomList.get(position).seller_image_url.toString())
            .into(holder.chat_seller_image)
    }

    override fun getItemCount(): Int {
        return chattingRoomList.size
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val chat_otherperson=itemView.findViewById<TextView>(R.id.chat_otherperson)
        val chat_post_title=itemView.findViewById<TextView>(R.id.chat_post_title)
        val chat_post_image=itemView.findViewById<ImageView>(R.id.chat_post_image)
        val chat_seller_image=itemView.findViewById<ImageView>(R.id.chat_profile)
    }

}
