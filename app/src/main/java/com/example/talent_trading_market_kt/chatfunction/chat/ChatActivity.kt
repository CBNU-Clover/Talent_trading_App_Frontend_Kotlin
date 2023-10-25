package com.example.talent_trading_market_kt.chatfunction.chat

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
import java.util.*
import java.util.concurrent.TimeUnit


class ChatActivity : AppCompatActivity(), TextWatcher, View.OnClickListener {
    override fun onResume() {
        super.onResume()
        updateChatscreen()
    }

    private fun updateChatscreen(){

        if(App.prefs.trade_status_complete=="success")
        {
            Log.v("거래3","성공")
            var postId:Long
            postId=intent.getStringExtra("postId").toString().toLong()
            val jsonObject = JSONObject()
            s=chat_postprice.text.toString()
            jsonObject.put("type", "TRADING_ACCEPT")
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
                submitTalk(s,formattedTime,"trading_accept_right",postId,App.prefs.nickname.toString())
                App.prefs.trade_status_complete="false"
            }
            else
            {
                val formattedTime = "$amOrPm $hour:$minute"
                jsonObject.put("date", formattedTime)
                stomp.send("/pub/chatroom", jsonObject.toString()).subscribe()
                submitTalk(s,formattedTime,"trading_accept_right",postId,App.prefs.nickname.toString())
                App.prefs.trade_status_complete="false"
            }
        }
        }


    lateinit var talkAdapter: TalkAdapter
    lateinit var stompConnection:Disposable
    lateinit var topic:Disposable
    lateinit var binding:ChatscreenBinding
    lateinit var chat_backbt:ImageButton
    lateinit var chat_person:TextView
    lateinit var chat_postname:TextView
    lateinit var chat_postprice:TextView
    lateinit var chat_plus_bt:ImageButton
    lateinit var container:FrameLayout
    lateinit var start_trade_bt:Button

    var roomId:Long=0
    val URL="ws://192.168.45.103:8080/ws/websocket"
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
        start_trade_bt=findViewById(R.id.start_trade)
        var seller:String
        var postname:String
        var postprice:String
        var postId:Long
        var trade_status:String
        roomId= intent.getStringExtra("roomId").toString().toLong()
        seller= intent.getStringExtra("seller").toString()
        postname= intent.getStringExtra("board_name").toString()
        postprice=intent.getStringExtra("board_price").toString()
        postId=intent.getStringExtra("postId").toString().toLong()
        trade_status=intent.getStringExtra("trade").toString()
        chat_person.text=seller
        chat_postname.text=postname
        chat_postprice.text=postprice

       chat_postname.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val intent=Intent(this@ChatActivity, SearchOneBoardActivity::class.java)
                intent.putExtra("Search_Id",postId.toString())
                startActivity(intent)

            }
        })
        chat_backbt.setOnClickListener {
            finish()
        }

        start_trade_bt.setOnClickListener {
            if(App.prefs.nickname.toString().trim()!=seller.trim())
            {
                Log.v("로그인", App.prefs.nickname!!)
                Log.v("작성자",seller)
                val jsonObject = JSONObject()
                s=chat_postprice.text.toString()
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
                    submitTalk(s,formattedTime,"trading_request_right",postId,App.prefs.nickname.toString())
                }
                else
                {
                    val formattedTime = "$amOrPm $hour:$minute"
                    jsonObject.put("date", formattedTime)
                    stomp.send("/pub/chatroom", jsonObject.toString()).subscribe()
                    submitTalk(s,formattedTime,"trading_request_right",postId,App.prefs.nickname.toString())
                }
            }
            if(App.prefs.nickname.toString()==seller)
            {
                Log.v("로그인2", App.prefs.nickname!!)
                Log.v("작성자2",seller)
                Toast.makeText(this@ChatActivity, "작성자만 거래 요청 가능합니다!!", Toast.LENGTH_SHORT)
                    .show()
            }

        }


        /*chat_plus_bt.setOnClickListener {
            val dialogView: View = LayoutInflater.from(this).inflate(R.layout.chatplus, null)
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setView(dialogView)
            val dialog: AlertDialog = builder.create()
            dialog.show()
            val gopaybt = dialogView.findViewById<ImageView>(R.id.imageView6)

            gopaybt.setOnClickListener {
                // 클릭 이벤트 처리
                val jsonObject = JSONObject()
                s=chat_postprice.text.toString()
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
                    dialog.dismiss()
                    submitTalk(s,formattedTime,"trading_request_right",postId,App.prefs.nickname.toString())
                }
                else
                {
                    val formattedTime = "$amOrPm $hour:$minute"
                    jsonObject.put("date", formattedTime)
                    stomp.send("/pub/chatroom", jsonObject.toString()).subscribe()
                    dialog.dismiss()
                    submitTalk(s,formattedTime,"trading_request_right",postId,App.prefs.nickname.toString())
                }
            }
        }*/

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
                            val type=chatHistoryDTO.type
                            if((type!="trading_request")&&(type!="trading_accept"))
                            {
                                val talk = if (sender !=App.prefs.nickname) {
                                    Talk(content.toString(),date.toString(),"left",postId,sender.toString())
                                } else {
                                    Talk(content.toString(),date.toString(), "right",postId,sender.toString())
                                }
                                talkAdapter.addItem(talk)
                            }
                            else if(type=="trading_request")
                            {
                                val talk = if (sender !=App.prefs.nickname) {
                                    Talk(content.toString(),date.toString(),"trading_request_left",postId,sender.toString())
                                } else {
                                    Talk(content.toString(),date.toString(), "trading_request_right",postId,sender.toString())
                                }
                                talkAdapter.addItem(talk)
                            }
                            else if(type=="trading_accept")
                            {
                                val talk = if (sender !=App.prefs.nickname) {
                                    Talk(content.toString(),date.toString(),"trading_accept_left",postId,sender.toString())
                                } else {
                                    Talk(content.toString(),date.toString(), "trading_accept_right",postId,sender.toString())
                                }
                                talkAdapter.addItem(talk)
                            }
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
                            val type=JSONObject(it).get("type")
                            if(sender!=App.prefs.nickname)
                            {
                                val talk:Talk
                                if(type=="TRADING_REQUEST")
                                {
                                    Log.v("타입",type.toString())
                                    talk= Talk(content.toString(), date.toString(),"trading_request_left",postId,sender.toString())

                                }
                                else if(type=="TRADING_ACCEPT")
                                {
                                    Log.v("타입",type.toString())
                                    talk= Talk(content.toString(), date.toString(),"trading_accept_left",postId,sender.toString())
                                }
                                else
                                {
                                    Log.v("타입",type.toString())
                                    talk= Talk(content.toString(), date.toString(),"left",postId,sender.toString())
                                }
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
        var postId:Long
        postId=intent.getStringExtra("postId").toString().toLong()
        //버튼 눌렀을때 서버로 채팅 전송
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
                    submitTalk(s,formattedTime,"right",postId,App.prefs.nickname.toString())
                }
                else
                {
                    val formattedTime = "$amOrPm $hour:$minute"
                    jsonObject.put("date", formattedTime)

                    stomp.send("/pub/chatroom", jsonObject.toString()).subscribe()
                    submitTalk(s,formattedTime,"right",postId,App.prefs.nickname.toString())
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

    // 버튼 눌렀을때 화면에 채팅 표시
    fun submitTalk(content:String,date:String,type:String,postId:Long,sender:String) {
        val talk= Talk(content,date,type,postId,sender.toString())
        talkAdapter.addItem(talk)
        binding.chat.smoothScrollToPosition(talkAdapter.lst.size-1)
        talkAdapter.notifyItemChanged(talkAdapter.lst.size-1)
        binding.talk.text = null
    }
}