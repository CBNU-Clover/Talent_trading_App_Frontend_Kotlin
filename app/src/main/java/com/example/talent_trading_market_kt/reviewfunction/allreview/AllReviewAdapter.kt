
package com.example.talent_trading_market_kt.reviewfunction.allreview
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.retrofit.App
import com.example.talent_trading_market_kt.reviewfunction.dto.ReviewReadResponse

class AllReviewAdapter(val reviews: List<ReviewReadResponse>): RecyclerView.Adapter<AllReviewAdapter.CustomViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllReviewAdapter.CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.review_list,parent,false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
            }
        }
    }

    override fun onBindViewHolder(holder: AllReviewAdapter.CustomViewHolder, position: Int) {
        holder.review_nickname.text = reviews.get(position).writerNickname
        holder.review_date.text = reviews.get(position).date
        holder.content_review.text = reviews.get(position).content
        val rating = reviews.get(position).rating?.toFloat()
        holder.review_score.text = rating.toString()
        if (rating != null) {
            holder.review_rating.rating = rating
        }
        Glide.with(holder.itemView.context)
            .load(App.prefs.image+reviews.get(position).review_writer_image_url.toString())
            .dontAnimate()
            .format(DecodeFormat.PREFER_ARGB_8888)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.review_writer_photo)
    }
    override fun getItemCount(): Int {
        return reviews.size
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val review_nickname=itemView.findViewById<TextView>(R.id.review_nickname) // 제목
        val review_rating=itemView.findViewById<RatingBar>(R.id.review_rating)
        val review_score=itemView.findViewById<TextView>(R.id.review_score)
        val review_date=itemView.findViewById<TextView>(R.id.review_date)
        val content_review=itemView.findViewById<TextView>(R.id.content_review)
        val review_writer_photo=itemView.findViewById<ImageView>(R.id.review_writer_photo)
    }

}
