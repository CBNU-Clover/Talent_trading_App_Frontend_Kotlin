
package com.example.talent_trading_market_kt.boardfunction.postsearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.chatfunction.api.ChatFunctionApi
import com.example.talent_trading_market_kt.chatfunction.chat.ChatActivity
import com.example.talent_trading_market_kt.chatfunction.dto.ChattingRoomDTO
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostReadResponse
import com.example.talent_trading_market_kt.retrofit.App
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.example.talent_trading_market_kt.reviewfunction.allreview.AllReview
import com.example.talent_trading_market_kt.reviewfunction.allreview.ThreeReviewAdapter
import com.example.talent_trading_market_kt.reviewfunction.api.ReviewFunctionApi
import com.example.talent_trading_market_kt.reviewfunction.dto.ReviewReadResponse
import kotlinx.android.synthetic.main.one_board_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchOneBoardActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        updateBoard()
    }

    private fun updateBoard() {
        var Id: Long
        Id = intent.getStringExtra("Search_Id").toString().toLong()
        //게시물 리뷰 평점 가져오는 것
        val service = RetrofitConnection.getInstance().create(ReviewFunctionApi::class.java)
        if (service != null) {
            service.getPostAvg(Id).enqueue(object : Callback<Double> {
                override fun onResponse(call: Call<Double>, response: Response<Double>) {
                    if (response.isSuccessful) {
                        var rating_av: Double
                        rating_av = response.body()!!
                        val formattedRating = String.format("%.1f", rating_av)
                        one_av.text = formattedRating.toFloat().toString()
                        one_rating_av.rating = rating_av.toFloat()
                    }
                }

                override fun onFailure(call: Call<Double?>, t: Throwable) {

                }

            })
        }


        if (service != null)
        {
            var Id: Long
            Id = intent.getStringExtra("Search_Id").toString().toLong()
            val service = RetrofitConnection.getInstance().create(ReviewFunctionApi::class.java)

                service.getAllPostReview(Id).enqueue(object : Callback<List<ReviewReadResponse>> {
                    override fun onResponse(
                        call: Call<List<ReviewReadResponse>>,
                        response: Response<List<ReviewReadResponse>>
                    ) {
                        if (response.isSuccessful) {
                            var reviewlist: List<ReviewReadResponse>;
                            reviewlist = response.body()!!;
                            one_reviewsize.text = "구매 후기 " + reviewlist.size.toString() + "개"
                            oneboard_reviews.layoutManager = LinearLayoutManager(
                                this@SearchOneBoardActivity,
                                LinearLayoutManager.VERTICAL, false
                            )
                            oneboard_reviews.setHasFixedSize(true)
                            oneboard_reviews.adapter = ThreeReviewAdapter(reviewlist)
                        }
                    }

                    override fun onFailure(call: Call<List<ReviewReadResponse>?>, t: Throwable) {

                    }

                })

        }
    }

        lateinit var writerNickname: TextView
        lateinit var title: TextView
        lateinit var content: TextView
        lateinit var board_price: TextView
        lateinit var searchone_date: TextView
        lateinit var searchone_content: TextView
        lateinit var back_button: ImageButton
        lateinit var chat_button: Button
        lateinit var go_review_bt: Button
        lateinit var one_av: TextView
        lateinit var one_rating_av: RatingBar
        lateinit var one_rating_av_up: TextView
        lateinit var one_reviewsize: TextView
        lateinit var one_board_image:ImageView
        lateinit var writer_photo:ImageView
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.one_board_page)
            var Id: Long
            one_reviewsize = findViewById(R.id.one_reviewsize)
            writerNickname = findViewById(R.id.searchone_writer)
            title = findViewById(R.id.searchone_title)
            chat_button = findViewById(R.id.chat)
            content = findViewById(R.id.searchone_content)
            one_av = findViewById(R.id.one_av)
            one_rating_av = findViewById(R.id.one_rating_av)
            board_price = findViewById(R.id.board_price)
            searchone_date = findViewById(R.id.searchone_date)
            searchone_content = findViewById(R.id.searchone_content)
            back_button = findViewById(R.id.back_button)
            go_review_bt = findViewById(R.id.goreview)
            one_board_image=findViewById(R.id.oneboard_image)
            writer_photo=findViewById(R.id.writer_photo)
            Id = intent.getStringExtra("Search_Id").toString().toLong()

            //뒤로 가기 버튼
            back_button.setOnClickListener {
                finish()
            }

            //리뷰 보러가는 버튼
            go_review_bt.setOnClickListener {
                val intent = Intent(this@SearchOneBoardActivity, AllReview::class.java)
                intent.putExtra("postId", Id.toString())
                startActivity(intent)
            }
            //채팅하기 버튼
            chat_button.setOnClickListener {
                if (writerNickname.text == App.prefs.nickname) {
                    Toast.makeText(this@SearchOneBoardActivity, "자신의 게시물입니다!!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val service =
                        RetrofitConnection.getInstance().create(ChatFunctionApi::class.java)
                    if (service != null) {
                        service.confirmChattingRoom(Id).enqueue(object : Callback<Int> {
                            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                                if (response.isSuccessful) {
                                    val confirm = response.body()
                                    if (confirm == 1) {
                                        Toast.makeText(
                                            this@SearchOneBoardActivity,
                                            "채팅방이 이미 있습니다!!",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    } else if (confirm == 0) {
                                        var chattingRoomDTO = ChattingRoomDTO()
                                        chattingRoomDTO.postId = Id
                                        chattingRoomDTO.seller = writerNickname.text.toString()
                                        service.createRoom(chattingRoomDTO)
                                            .enqueue(object : Callback<Long> {
                                                override fun onResponse(
                                                    call: Call<Long>,
                                                    response: Response<Long>
                                                ) {
                                                    if (response.isSuccessful) {
                                                        val roomId = response.body()
                                                        if (roomId != null) {
                                                            val intent = Intent(
                                                                this@SearchOneBoardActivity,
                                                                ChatActivity::class.java
                                                            )
                                                            intent.putExtra(
                                                                "roomId",
                                                                roomId.toString()
                                                            )
                                                            intent.putExtra(
                                                                "seller",
                                                                writerNickname.text.toString()
                                                            )
                                                            intent.putExtra(
                                                                "board_name",
                                                                title.text.toString()
                                                            )
                                                            intent.putExtra(
                                                                "board_price",
                                                                board_price.text.toString()
                                                            )
                                                            intent.putExtra(
                                                                "postId",Id.toString()
                                                            )
                                                            startActivity(intent)
                                                        }
                                                    }

                                                }

                                                override fun onFailure(
                                                    call: Call<Long?>,
                                                    t: Throwable
                                                ) {
                                                    Toast.makeText(
                                                        this@SearchOneBoardActivity,
                                                        "오류",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                }
                                            })
                                    }
                                }
                            }

                            override fun onFailure(call: Call<Int>, t: Throwable) {
                                Toast.makeText(
                                    this@SearchOneBoardActivity,
                                    "오류",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                        })
                    }
                }

            }

            //한 게시물의 정보 가져오는 API
            val boardservice = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)
            if (boardservice != null) {
                boardservice.readPost(Id).enqueue(object : Callback<PostReadResponse> {
                    override fun onResponse(
                        call: Call<PostReadResponse>,
                        response: Response<PostReadResponse>
                    ) {
                        if (response.isSuccessful) {
                            var post: PostReadResponse
                            post = response.body()!!
                            writerNickname.text = post.writerNickname
                            title.text = post.postName
                            content.text = post.content
                            searchone_date.text = post.date
                            App.prefs.first_chat_image=post.board_image_url.toString()
                            board_price.text = post.price.toString() + "원"
                            Glide.with(this@SearchOneBoardActivity)
                                .load(App.prefs.image+post.board_image_url.toString())
                                .dontAnimate()
                                .format(DecodeFormat.PREFER_ARGB_8888)
                                .into(one_board_image)
                           Glide.with(this@SearchOneBoardActivity)
                                .load(App.prefs.image+post.writer_image_url.toString())
                                .dontAnimate()
                                .format(DecodeFormat.PREFER_ARGB_8888)
                                .into(writer_photo)
                        }
                    }

                    override fun onFailure(call: Call<PostReadResponse?>, t: Throwable) {

                    }

                })
            }

        }
    }



