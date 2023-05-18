package com.example.talent_trading_market_kt.boardfunction.postsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.BoardFunctionApi
import com.example.talent_trading_market_kt.dto.PostSearch
import com.example.talent_trading_market_kt.response.PostSearchResult
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.activity_search_board.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchBoardActivity : AppCompatActivity() {
    lateinit var postname: EditText
    lateinit var search_bt:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_board)
        postname=findViewById(R.id.keyword)
        search_bt=findViewById(R.id.search_bt)

        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)
        search_bt.setOnClickListener {
            val postSearch= PostSearch()
            val postname=postname
            postSearch.postName= postname.text.toString()
            if(service!=null)
            {
                service.postsearch(postSearch).enqueue(object : Callback<List<PostSearchResult>> {
                    override fun onResponse(call: Call<List<PostSearchResult>>, response: Response<List<PostSearchResult>>) {
                        if (response.isSuccessful) {
                            var searchboardList:List<PostSearchResult>;
                            searchboardList= response.body()!!;
                            search_board.layoutManager= LinearLayoutManager(this@SearchBoardActivity,
                                LinearLayoutManager.VERTICAL,false)
                            search_board.setHasFixedSize(true)
                            search_board.adapter= SearchBoardAdapter(searchboardList)
                        }
                    }

                    override fun onFailure(call: Call<List<PostSearchResult>?>, t: Throwable) {
                        Toast.makeText(this@SearchBoardActivity, "다시 검색버튼을 눌러주세요", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
            }

        }

    }
}