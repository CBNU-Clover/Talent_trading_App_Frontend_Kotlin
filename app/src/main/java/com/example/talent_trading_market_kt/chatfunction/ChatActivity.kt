package com.example.talent_trading_market_kt.chatfunction

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.databinding.ChatlistPageBinding
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.util.concurrent.TimeUnit


class ChatActivity : AppCompatActivity(), TextWatcher, View.OnClickListener {
   lateinit var binding: ChatlistPageBinding
    lateinit var talkAdapter: TalkAdapter
    lateinit var stompConnection:Disposable
    lateinit var topic:Disposable
    val URL="ws://192.168.45.166:8080/ws/websocket"
    val intervalMillis = 5000L
    val client = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()
    val stomp = StompClient(client, intervalMillis).apply { this@apply.url = URL }
    //var changeType = false
    var s= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.chatlist_page)
        binding.talk.addTextChangedListener(this)
        talkAdapter = TalkAdapter()
        binding.chat.adapter = talkAdapter
        binding.chat.setHasFixedSize(false)
        binding.chat.layoutManager = LinearLayoutManager(this)
        val service = RetrofitConnection.getInstance().create(ChatFunctionApi::class.java)
        if (service != null) {
            service.ChatHistory().enqueue(object : retrofit2.Callback<List<ChatHistoryDTO>> {
                override fun onResponse(call: retrofit2.Call<List<ChatHistoryDTO>>, response: retrofit2.Response<List<ChatHistoryDTO>>) {
                    if (response.isSuccessful) {
                        var chatlist = response.body() ?: emptyList()
                        for (chatHistoryDTO in chatlist) {
                            val sender = chatHistoryDTO.sender
                            val content = chatHistoryDTO.content
                            val talk = if (sender == "striker") {
                                Talk(content.toString(), "left")
                            } else {
                                Talk(content.toString(), "right")
                            }
                            talkAdapter.addItem(talk)
                        }
                        talkAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: retrofit2.Call<List<ChatHistoryDTO>?>, t: Throwable) {
                    Toast.makeText(this@ChatActivity, "오류", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
        // connect
        stompConnection = stomp.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {

                    // subscribe
                    topic = stomp.join("/sub/chatroom/376")
                        .subscribe {it->
                            val content=JSONObject(it).get("content")
                            val sender=JSONObject(it).get("sender")
                            if(sender=="striker")
                            {
                              val talk=Talk(content.toString(),"left")

                                runOnUiThread{
                                    talkAdapter.addItem(talk)
                                    binding.chat.smoothScrollToPosition(talkAdapter.lst.size-1)
                                    talkAdapter.notifyItemChanged(talkAdapter.lst.size-1)
                            }
                            }
                        }

//                // unsubscribe
//                topic.dispose()

                    // send


//                stomp.send("/app/hello", Base64.getEncoder().encodeToString(File("/home/bishoybasily/Desktop/input.jpg").readBytes())).subscribe {
//                    if (it) {
//                    }
//                }


                }
                Event.Type.CLOSED -> {

                }
                Event.Type.ERROR -> {

                }
            }
        }
    }


    override fun onClick(view: View?) {
        when(view?.id){
            R.id.submit_talk->{
                val jsonObject=JSONObject()
                jsonObject.put("type","MESSAGE")
                jsonObject.put("sender","messi")
                jsonObject.put("roomId",376)
                jsonObject.put("content",s)
                stomp.send("/pub/chatroom",jsonObject.toString()).subscribe()
                submitTalk(s,"right")
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
        this.s = s.toString()
    }

    override fun afterTextChanged(s: Editable?) {

    }

    fun submitTalk(content:String,type:String) {
        /*val talk = if (changeType) {
            Talk(s, "right")
        } else {
            Talk(s, "left")
        }*/
        val talk=Talk(content,type)
        talkAdapter.addItem(talk)
        binding.chat.smoothScrollToPosition(talkAdapter.lst.size-1)
        talkAdapter.notifyItemChanged(talkAdapter.lst.size-1)
        //changeType = !changeType
        binding.talk.text = null
    }
}