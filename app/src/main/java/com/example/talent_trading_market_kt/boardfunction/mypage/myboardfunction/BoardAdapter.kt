package com.example.talent_trading_market_kt.boardfunction.mypage.myboardfunction

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.response.PostGetAllBoard

class BoardAdapter(var boardList: List<PostGetAllBoard>): RecyclerView.Adapter<BoardAdapter.CustomViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.myboardlist_item,parent,false)
        // parent ( 리사이클뷰를 적용할 activity ) 와 boarlist_item xml 화면을 붙인다(inflate)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                val boards: PostGetAllBoard =boardList.get(curPos)
                val id: Long? =boards.id
                //val postDeleteBoard=PostDeleteBoard()
                //postDeleteBoard.delete_id=id
                //Toast.makeText(parent.context,"Id:${boards.id} 제목:${boards.postName} 내용:${boards.content}",Toast.LENGTH_SHORT).show()
                val intent=Intent(parent.context, OneBoardActivity::class.java)
                intent.putExtra("postName",boards.postName)
                intent.putExtra("content",boards.content)
                intent.putExtra("Id",boards.id.toString())
                parent.context.startActivity(intent)

            }
        }
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //실질적으로 연결해주는 부분 // 스크롤 내릴때 지속적으로 호출이 되는 곳
        holder.title.text=boardList.get(position).postName
        holder.content.text=boardList.get(position).content
    }

    override fun getItemCount(): Int {
        return boardList.size
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val title=itemView.findViewById<TextView>(R.id.my_title) // 제목
        val content=itemView.findViewById<TextView>(R.id.my_content) // 내용
    }

}