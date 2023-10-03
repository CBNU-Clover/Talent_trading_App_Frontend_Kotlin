package com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.response.PostGetAllBoard

class MyBoardAdapter(var boardList: List<PostGetAllBoard>): RecyclerView.Adapter<MyBoardAdapter.CustomViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_board,parent,false)
        // parent ( 리사이클뷰를 적용할 activity ) 와 boarlist_item xml 화면을 붙인다(inflate)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                val boards: PostGetAllBoard =boardList.get(curPos)
                val id: Long? =boards.id
                val intent=Intent(parent.context, MyOneBoardActivity::class.java)
                intent.putExtra("my_postId",boards.id.toString())
                parent.context.startActivity(intent)
            }
        }
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //실질적으로 연결해주는 부분 // 스크롤 내릴때 지속적으로 호출이 되는 곳
        holder.title.text=boardList.get(position).postName
        holder.price.text= boardList.get(position).price.toString()+"원"
        holder.my_date.text=boardList.get(position).date
        holder.my_review_size.text=boardList.get(position).my_review_size.toString()
    }

    override fun getItemCount(): Int {
        return boardList.size
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val title=itemView.findViewById<TextView>(R.id.all_title) // 제목
        val price=itemView.findViewById<TextView>(R.id.all_price)//가격
        val my_date=itemView.findViewById<TextView>(R.id.all_time) // 작성일자
        var my_review_size=itemView.findViewById<TextView>(R.id.board_review_size)
    }

}