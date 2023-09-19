
package com.example.talent_trading_market_kt.chatfunction.chattingroom


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.chatfunction.response.ChattingRoomListDTO

class ChattingRoomAdapter(val chattingRoomList: List<ChattingRoomListDTO>): RecyclerView.Adapter<ChattingRoomAdapter.CustomViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChattingRoomAdapter.CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.chat_list,parent,false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                /*val search_boards: PostSearchResult =boardList.get(curPos)
                val id: Long? =search_boards.id
                val intent=Intent(parent.context,SearchOneBoardActivity::class.java)
                intent.putExtra("Search_writerNickname",search_boards.writerNickname)
                intent.putExtra("Search_postName",search_boards.postName)
                intent.putExtra("Search_content",search_boards.content)
                intent.putExtra("Search_Id",search_boards.id.toString())
                intent.putExtra("Search_price",search_boards.price.toString())
                intent.putExtra("Search_date",search_boards.date)
                parent.context.startActivity(intent)*/

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
