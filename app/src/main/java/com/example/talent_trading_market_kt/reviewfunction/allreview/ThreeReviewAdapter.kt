package com.example.talent_trading_market_kt.reviewfunction.allreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.reviewfunction.dto.ReviewReadResponse

class ThreeReviewAdapter(val reviews: List<ReviewReadResponse>): RecyclerView.Adapter<ThreeReviewAdapter.CustomViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreeReviewAdapter.CustomViewHolder{
        val view=LayoutInflater.from(parent.context).inflate(R.layout.review_list,parent,false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
            }
        }
    }

    override fun onBindViewHolder(holder: ThreeReviewAdapter.CustomViewHolder, position: Int) {
        holder.review_nickname.text = reviews.get(position).writerNickname
        holder.review_date.text = reviews.get(position).date
        holder.content_review.text = reviews.get(position).content
        val rating = reviews.get(position).rating?.toFloat()
        holder.review_score.text = rating.toString()
        if (rating != null) {
            holder.review_rating.rating = rating
        }
    }
    override fun getItemCount(): Int {
        return minOf(3, reviews.size)
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val review_nickname=itemView.findViewById<TextView>(R.id.review_nickname) // 제목
        val review_rating=itemView.findViewById<RatingBar>(R.id.review_rating)
        val review_score=itemView.findViewById<TextView>(R.id.review_score)
        val review_date=itemView.findViewById<TextView>(R.id.review_date)
        val content_review=itemView.findViewById<TextView>(R.id.content_review)
    }

}
