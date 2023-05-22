
package com.example.talent_trading_market_kt.boardfunction.postsearch

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostDeleteBoard
import com.example.talent_trading_market_kt.response.PostGetAllBoard
import com.example.talent_trading_market_kt.response.PostSearchResult
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import java.lang.Exception

class SearchBoardAdapter(val boardList: List<PostSearchResult>): RecyclerView.Adapter<SearchBoardAdapter.CustomViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBoardAdapter.CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.searchresult_item,parent,false)
        // parent ( 리사이클뷰를 적용할 activity ) 와 boarlist_item xml 화면을 붙인다(inflate)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                val search_boards: PostSearchResult =boardList.get(curPos)
                val id: Long? =search_boards.id
                Toast.makeText(parent.context,"Id:${search_boards.id} 작성자:${search_boards.writerNickname} 제목:${search_boards.postName} 내용:${search_boards.content}",Toast.LENGTH_SHORT).show()
                val intent=Intent(parent.context,SearchOneBoardActivity::class.java)
                intent.putExtra("Search_writerNickname",search_boards.writerNickname)
                intent.putExtra("Search_postName",search_boards.postName)
                intent.putExtra("Search_content",search_boards.content)
                intent.putExtra("Search_Id",search_boards.id.toString())
                parent.context.startActivity(intent)

            }
        }
    }

    override fun onBindViewHolder(holder: SearchBoardAdapter.CustomViewHolder, position: Int) {
        //실질적으로 연결해주는 부분 // 스크롤 내릴때 지속적으로 호출이 되는 곳
        holder.writerNickname.text=boardList.get(position).writerNickname
        holder.title.text=boardList.get(position).postName
        holder.content.text=boardList.get(position).content
    }

    override fun getItemCount(): Int {
        return boardList.size
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val title=itemView.findViewById<TextView>(R.id.search_title) // 제목
        val content=itemView.findViewById<TextView>(R.id.search_content) // 내용
        val writerNickname=itemView.findViewById<TextView>(R.id.search_writer) // 작성자
    }

}
