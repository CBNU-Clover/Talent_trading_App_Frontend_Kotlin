package com.example.talent_trading_market_kt.chatfunction.chat


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.chatfunction.api.ChatFunctionApi
import com.example.talent_trading_market_kt.chatfunction.dto.ChattingRoomDTO
import com.example.talent_trading_market_kt.databinding.ChatlistPageBinding
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit


class MakeChatRoom : AppCompatActivity(), TextWatcher, View.OnClickListener {
    lateinit var binding: ChatlistPageBinding
    lateinit var talkAdapter: TalkAdapter
    lateinit var stompConnection:Disposable
    lateinit var topic:Disposable
    var postId:Long=0
    var roomId:Long=0;
    val URL="ws://192.168.45.30:8080/ws/websocket"
    val intervalMillis = 5000L
    val client = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()
    val stomp = StompClient(client, intervalMillis).apply { this@apply.url = URL }
    var s= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.chatlist_page)
        binding.talk.addTextChangedListener(this)
        var seller:String
        /*binding.talk.setOnClickListener {
            // 채팅 화면을 가장 아래로 스크롤합니다.
            binding.chat.scrollToPosition(talkAdapter.itemCount - 1)
        }*/
        binding.talk.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.chat.postDelayed({
                    binding.chat.smoothScrollToPosition(talkAdapter.lst.size - 1)
                }, 200)
            }
        }
        binding.linearLayoutTalk.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.chat.postDelayed({
                    binding.chat.smoothScrollToPosition(talkAdapter.lst.size - 1)
                }, 200)
            }
        }
        /*binding.talk.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // 포커스를 얻을 때 채팅 화면을 가장 아래로 스크롤합니다.
                binding.chat.scrollToPosition(talkAdapter.itemCount - 1)
            }
        }*/
        postId= intent.getStringExtra("postId").toString().toLong()
        seller= intent.getStringExtra("seller").toString()
        talkAdapter = TalkAdapter()
        binding.chat.adapter = talkAdapter
        binding.chat.setHasFixedSize(false)
        binding.chat.layoutManager = LinearLayoutManager(this)
        val service = RetrofitConnection.getInstance().create(ChatFunctionApi::class.java)
        if (service != null) {
            var chattingRoomDTO=ChattingRoomDTO()
            chattingRoomDTO.postId=postId
            chattingRoomDTO.seller=seller
            service.createRoom(chattingRoomDTO).enqueue(object : Callback<Long> {
                override fun onResponse(call: Call<Long>, response: Response<Long>) {
                    if(response.isSuccessful)
                    {
                        roomId=response.body()!!
                    }
                }

                override fun onFailure(call: Call<Long?>, t: Throwable) {
                    Toast.makeText(this@MakeChatRoom, "오류", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
        // connect
        stompConnection = stomp.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {

                    // subscribe
                    topic = stomp.join("/sub/chatroom/${roomId}")
                        .subscribe {it->
                            val content=JSONObject(it).get("content")
                            val sender=JSONObject(it).get("sender")
                            if(sender==seller)
                            {
                                val talk= Talk(content.toString(),"left")

                                runOnUiThread{
                                    talkAdapter.addItem(talk)
                                    binding.chat.smoothScrollToPosition(talkAdapter.lst.size-1)
                                    talkAdapter.notifyItemChanged(talkAdapter.lst.size-1)
                                }
                            }
                        }
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
                jsonObject.put("sender","shin")
                jsonObject.put("roomId",roomId)
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
        val talk= Talk(content,type)
        talkAdapter.addItem(talk)
        binding.chat.smoothScrollToPosition(talkAdapter.lst.size-1)
        talkAdapter.notifyItemChanged(talkAdapter.lst.size-1)
        binding.talk.text = null
    }
}