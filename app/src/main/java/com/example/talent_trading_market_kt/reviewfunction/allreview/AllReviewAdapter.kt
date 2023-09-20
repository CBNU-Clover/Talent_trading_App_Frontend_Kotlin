
package com.example.talent_trading_market_kt.reviewfunction.allreview
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.reviewfunction.dto.ReviewReadResponse

class AllReviewAdapter(val reviews: List<ReviewReadResponse>): RecyclerView.Adapter<AllReviewAdapter.CustomViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllReviewAdapter.CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.review_list,parent,false)
        // parent ( 리사이클뷰를 적용할 activity ) 와 boarlist_item xml 화면을 붙인다(inflate)
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

    override fun onBindViewHolder(holder: AllReviewAdapter.CustomViewHolder, position: Int) {
        //실질적으로 연결해주는 부분 // 스크롤 내릴때 지속적으로 호출이 되는 곳
        //holder.writerNickname.text=boardList.get(position).writerNickname
        holder.review_nickname.text=reviews.get(position).writerNickname
       // holder.content.text=boardList.get(position).content
        holder.review_date.text= reviews.get(position).date
        holder.content_review.text= reviews.get(position).content
    }
    /*fun updateReceiptsList(reviews: List<ReviewReadResponse>) {
        listViewItemList.clear()
        listViewItemList.addAll(reviews)
        this.notifyDataSetChanged()

    }
*/
    override fun getItemCount(): Int {
        return reviews.size
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val review_nickname=itemView.findViewById<TextView>(R.id.review_nickname) // 제목
        //val content=itemView.findViewById<TextView>(R.id.all_content) // 내용
        //val writerNickname=itemView.findViewById<TextView>(R.id.all_writer) // 작성자
        val review_date=itemView.findViewById<TextView>(R.id.review_date)
        val content_review=itemView.findViewById<TextView>(R.id.content_review)
    }

}
