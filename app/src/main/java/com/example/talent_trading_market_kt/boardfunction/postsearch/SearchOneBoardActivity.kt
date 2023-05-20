/*

package com.example.talent_trading_market_kt.boardfunction.postsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.talent_trading_market_kt.R
import org.w3c.dom.Text

class SearchOneBoardActivity : AppCompatActivity() {
    lateinit var writerNickname:TextView
    lateinit var title:TextView
    lateinit var content:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_one_board)
        var id:Long
        writerNickname=findViewById(R.id.one_writer)
        title=findViewById(R.id.one_title)
        content=findViewById(R.id.one_content)
        if(intent.hasExtra("Search_writerNickname"))
        {
            title.text=intent.getStringExtra("Search_writerNickname")

        }
        if(intent.hasExtra("Search_postName"))
        {
            title.text=intent.getStringExtra("Search_postName")

        }
        if(intent.hasExtra("Search_content"))
        {
            content.text=intent.getStringExtra("Search_content")
        }
    }
}
*/
