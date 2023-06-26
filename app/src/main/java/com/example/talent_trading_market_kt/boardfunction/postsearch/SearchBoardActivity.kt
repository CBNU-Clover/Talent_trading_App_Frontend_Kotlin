
package com.example.talent_trading_market_kt.boardfunction.postsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostSearch
import com.example.talent_trading_market_kt.response.postresponse.PostSearchResult
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.show_search_result_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchBoardActivity : AppCompatActivity() {
    lateinit var postname: EditText
    lateinit var search_bt:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_search_result_page)
        postname=findViewById(R.id.search_postName)
        search_bt=findViewById(R.id.search_button)
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
                            search_result_view.layoutManager= LinearLayoutManager(this@SearchBoardActivity,
                                LinearLayoutManager.VERTICAL,false)
                            search_result_view.setHasFixedSize(true)
                            search_result_view.adapter= SearchBoardAdapter(searchboardList)
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

