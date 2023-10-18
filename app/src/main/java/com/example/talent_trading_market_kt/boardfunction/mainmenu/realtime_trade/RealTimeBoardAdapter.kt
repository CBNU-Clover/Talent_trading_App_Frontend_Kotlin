package com.example.talent_trading_market_kt.boardfunction.mainmenu.realtime_trade

import com.example.talent_trading_market_kt.boardfunction.postsearch.SearchOneBoardActivity
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.response.postresponse.PostSearchResult
import com.example.talent_trading_market_kt.retrofit.App
import io.reactivex.Completable.error

class RealTimeBoardAdapter(var boardList: List<PostSearchResult>): RecyclerView.Adapter<RealTimeBoardAdapter.CustomViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealTimeBoardAdapter.CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_board,parent,false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                val search_boards: PostSearchResult =boardList.get(curPos)
                val id: Long? =search_boards.id
                val intent=Intent(parent.context, SearchOneBoardActivity::class.java)
                intent.putExtra("Search_Id",search_boards.id.toString())
                parent.context.startActivity(intent)

            }
        }
    }

    override fun onBindViewHolder(holder: RealTimeBoardAdapter.CustomViewHolder, position: Int) {
        //실질적으로 연결해주는 부분 // 스크롤 내릴때 지속적으로 호출이 되는 곳
        holder.title.text=boardList.get(position).postName
        holder.price.text= boardList.get(position).price.toString()+"원"
        holder.date.text= boardList.get(position).date
        holder.review_size.text= boardList.get(position).review_size.toString()
        Glide.with(holder.itemView.context)
            .load(App.prefs.image+boardList.get(position).image_url.toString())
            .dontAnimate()
            .format(DecodeFormat.PREFER_ARGB_8888)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.board_image)
    }

    override fun getItemCount(): Int {
        return minOf(3, boardList.size)
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val title=itemView.findViewById<TextView>(R.id.all_title) // 제목
        val price=itemView.findViewById<TextView>(R.id.all_price)
        val date=itemView.findViewById<TextView>(R.id.all_time)
        val review_size=itemView.findViewById<TextView>(R.id.board_review_size)
        val board_image=itemView.findViewById<ImageView>(R.id.board_image)
    }

}
