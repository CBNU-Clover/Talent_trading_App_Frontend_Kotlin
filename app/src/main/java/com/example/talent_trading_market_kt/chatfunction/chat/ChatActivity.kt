package com.example.talent_trading_market_kt.chatfunction.chat

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.postsearch.SearchOneBoardActivity
import com.example.talent_trading_market_kt.chatfunction.api.ChatFunctionApi
import com.example.talent_trading_market_kt.chatfunction.dto.ChatHistoryDTO
import com.example.talent_trading_market_kt.databinding.ChatscreenBinding
import com.example.talent_trading_market_kt.retrofit.App
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext
import java.util.*
import java.util.concurrent.TimeUnit


class ChatActivity : AppCompatActivity(), TextWatcher, View.OnClickListener {
    lateinit var talkAdapter: TalkAdapter
    lateinit var stompConnection:Disposable
    lateinit var topic:Disposable
    lateinit var binding:ChatscreenBinding
    lateinit var chat_backbt:ImageButton
    lateinit var chat_person:TextView
    lateinit var chat_postname:TextView
    lateinit var chat_postprice:TextView
    lateinit var chat_plus_bt:ImageButton

    var roomId:Long=0
    val URL="ws://192.168.45.174:8080/ws/websocket"
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
        binding = DataBindingUtil.setContentView(this,R.layout.chatscreen)
        binding.talk.addTextChangedListener(this)
        chat_backbt=findViewById(R.id.chat_quit)
        chat_person=findViewById(R.id.txt_TItle)
        chat_postname=findViewById(R.id.chat_postname)
        chat_postprice=findViewById(R.id.textView)
        chat_plus_bt=findViewById(R.id.chatplus)
        var seller:String
        var postname:String
        var postprice:String
        var Id:Long
        roomId= intent.getStringExtra("roomId").toString().toLong()
        seller= intent.getStringExtra("seller").toString()
        postname= intent.getStringExtra("board_name").toString()
        postprice=intent.getStringExtra("board_price").toString()
        Id=intent.getStringExtra("postId").toString().toLong()
        chat_person.text=seller
        chat_postname.text=postname
        chat_postprice.text=postprice


       chat_postname.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val intent=Intent(this@ChatActivity, SearchOneBoardActivity::class.java)
                intent.putExtra("Search_Id",Id.toString())
                startActivity(intent)

            }
        })



        chat_backbt.setOnClickListener {
            finish()
        }

        chat_plus_bt.setOnClickListener {

        }


        talkAdapter = TalkAdapter()
        binding.chat.adapter = talkAdapter
        binding.chat.setHasFixedSize(false)
        binding.chat.layoutManager = LinearLayoutManager(this)
        val service = RetrofitConnection.getInstance().create(ChatFunctionApi::class.java)
        if (service != null) {
            service.ChatHistory(roomId).enqueue(object : Callback<List<ChatHistoryDTO>> {
                override fun onResponse(call: Call<List<ChatHistoryDTO>>, response: Response<List<ChatHistoryDTO>>) {
                    if (response.isSuccessful) {
                        var chatlist = response.body() ?: emptyList()
                        for (chatHistoryDTO in chatlist) {
                            val sender = chatHistoryDTO.sender
                            val content = chatHistoryDTO.content
                            val date=chatHistoryDTO.date
                            val talk = if (sender !=App.prefs.nickname) {
                                Talk(content.toString(),date.toString(),"left")
                            } else {
                                Talk(content.toString(),date.toString(), "right")
                            }
                            talkAdapter.addItem(talk)
                        }
                        talkAdapter.notifyDataSetChanged()
                    }
                    binding.chat.scrollToPosition(talkAdapter.lst.size-1)
                }

                override fun onFailure(call: Call<List<ChatHistoryDTO>?>, t: Throwable) {
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
                    topic = stomp.join("/sub/chatroom/${roomId}")
                        .subscribe {it->
                            val content=JSONObject(it).get("content")
                            val sender=JSONObject(it).get("sender")
                            val date=JSONObject(it).get("date")
                            if(sender!=App.prefs.nickname)
                            {
                              val talk= Talk(content.toString(), date.toString(),"left")

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
        when(view?.id)
        {
            R.id.chatplus->{
                val jsonObject = JSONObject()
                jsonObject.put("type", "TRADING_REQUEST")
                jsonObject.put("sender", App.prefs.nickname)
                jsonObject.put("roomId", roomId)
                jsonObject.put("content", s)

                val calendar = Calendar.getInstance()
                val amOrPm = if (calendar.get(Calendar.AM_PM) == Calendar.AM) "오전" else "오후"
                var hour = calendar.get(Calendar.HOUR_OF_DAY) // 24시간 형식의 시간
                if(hour>12)
                {
                    hour=hour-12
                }
                val minute = calendar.get(Calendar.MINUTE)
                if(minute<10)
                {
                    val formattedTime = "$amOrPm $hour:0$minute"
                    jsonObject.put("date", formattedTime)

                    stomp.send("/pub/chatroom", jsonObject.toString()).subscribe()
                    submitTalk(s,formattedTime,"trading_request")
                }
                else
                {
                    val formattedTime = "$amOrPm $hour:$minute"
                    jsonObject.put("date", formattedTime)

                    stomp.send("/pub/chatroom", jsonObject.toString()).subscribe()
                    submitTalk(s,formattedTime,"trading_request")
                }
            }
        }
        when(view?.id){
            R.id.submit_talk->{
                val jsonObject = JSONObject()
                jsonObject.put("type", "MESSAGE")
                jsonObject.put("sender", App.prefs.nickname)
                jsonObject.put("roomId", roomId)
                jsonObject.put("content", s)

                val calendar = Calendar.getInstance()
                val amOrPm = if (calendar.get(Calendar.AM_PM) == Calendar.AM) "오전" else "오후"
                var hour = calendar.get(Calendar.HOUR_OF_DAY) // 24시간 형식의 시간
                if(hour>12)
                {
                    hour=hour-12
                }
                val minute = calendar.get(Calendar.MINUTE)
                if(minute<10)
                {
                    val formattedTime = "$amOrPm $hour:0$minute"
                    jsonObject.put("date", formattedTime)

                    stomp.send("/pub/chatroom", jsonObject.toString()).subscribe()
                    submitTalk(s,formattedTime,"right")
                }
                else
                {
                    val formattedTime = "$amOrPm $hour:$minute"
                    jsonObject.put("date", formattedTime)

                    stomp.send("/pub/chatroom", jsonObject.toString()).subscribe()
                    submitTalk(s,formattedTime,"right")
                }
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

    fun submitTalk(content:String,date:String,type:String) {
        val talk= Talk(content,date,type)
        talkAdapter.addItem(talk)
        binding.chat.smoothScrollToPosition(talkAdapter.lst.size-1)
        talkAdapter.notifyItemChanged(talkAdapter.lst.size-1)
        binding.talk.text = null
    }
}