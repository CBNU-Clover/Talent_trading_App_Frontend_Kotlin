
package com.example.talent_trading_market_kt.boardfunction.mainmenu.hottrade

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.response.postresponse.PostSearchResult

class HotTradeBoardAdapter(val boardList: List<PostSearchResult>): RecyclerView.Adapter<HotTradeBoardAdapter.CustomViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotTradeBoardAdapter.CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_hottrade,parent,false)
        // parent ( 리사이클뷰를 적용할 activity ) 와 boarlist_item xml 화면을 붙인다(inflate)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                val search_boards: PostSearchResult =boardList.get(curPos)
                val id: Long? =search_boards.id
                //Toast.makeText(parent.context,"Id:${search_boards.id} 작성자:${search_boards.writerNickname} 제목:${search_boards.postName} 내용:${search_boards.content}",Toast.LENGTH_SHORT).show()
                val intent=Intent(parent.context,HotTradeOneBoardActivity::class.java)
                intent.putExtra("HotTrade_writerNickname",search_boards.writerNickname)
                intent.putExtra("HotTrade_postName",search_boards.postName)
                intent.putExtra("HotTrade_content",search_boards.content)
                intent.putExtra("HotTrade_Id",search_boards.id.toString())
                intent.putExtra("HotTrade_price",search_boards.price.toString())
                intent.putExtra("HotTrade_date",search_boards.date)
                parent.context.startActivity(intent)

            }
        }
    }

    override fun onBindViewHolder(holder: HotTradeBoardAdapter.CustomViewHolder, position: Int) {
        //실질적으로 연결해주는 부분 // 스크롤 내릴때 지속적으로 호출이 되는 곳
        holder.hottrade_postname.text=boardList.get(position).postName
        holder.hottrade_price.text=boardList.get(position).price.toString()+"원"

    }

    override fun getItemCount(): Int {
        return boardList.size
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val hottrade_postname=itemView.findViewById<TextView>(R.id.hottrade_postname)
        val hottrade_price=itemView.findViewById<TextView>(R.id.hottrade_price)
    }

}
