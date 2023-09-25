
package com.example.talent_trading_market_kt.boardfunction.postsearch

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.response.postresponse.PostSearchResult

class SearchBoardAdapter(var boardList: List<PostSearchResult>): RecyclerView.Adapter<SearchBoardAdapter.CustomViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBoardAdapter.CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_board,parent,false)
        // parent ( 리사이클뷰를 적용할 activity ) 와 boarlist_item xml 화면을 붙인다(inflate)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                val search_boards: PostSearchResult =boardList.get(curPos)
                val id: Long? =search_boards.id
                val intent=Intent(parent.context,SearchOneBoardActivity::class.java)
                intent.putExtra("Search_Id",search_boards.id.toString())
                parent.context.startActivity(intent)

            }
        }
    }

    override fun onBindViewHolder(holder: SearchBoardAdapter.CustomViewHolder, position: Int) {
        //실질적으로 연결해주는 부분 // 스크롤 내릴때 지속적으로 호출이 되는 곳
        holder.title.text=boardList.get(position).postName
        holder.price.text= boardList.get(position).price.toString()+"원"
        holder.date.text= boardList.get(position).date
    }

    override fun getItemCount(): Int {
        return boardList.size
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val title=itemView.findViewById<TextView>(R.id.all_title) // 제목
        val price=itemView.findViewById<TextView>(R.id.all_price)
        val date=itemView.findViewById<TextView>(R.id.all_time)
    }

}
