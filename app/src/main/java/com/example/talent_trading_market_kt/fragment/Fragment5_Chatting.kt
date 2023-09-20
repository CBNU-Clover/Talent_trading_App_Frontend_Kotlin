package com.example.talent_trading_market_kt.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.chatfunction.api.ChatFunctionApi
import com.example.talent_trading_market_kt.chatfunction.chattingroom.ChattingRoomAdapter
import com.example.talent_trading_market_kt.chatfunction.response.ChattingRoomListDTO
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.android.synthetic.main.chattingroom_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment5_Chatting:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View=inflater.inflate(R.layout.chattingroom_list,container,false)
        val service = RetrofitConnection.getInstance().create(ChatFunctionApi::class.java)
        if(service!=null)
        {
            service.Rooms().enqueue(object : Callback<List<ChattingRoomListDTO>> {
                override fun onResponse(call: Call<List<ChattingRoomListDTO>>, response: Response<List<ChattingRoomListDTO>>) {
                    if (response.isSuccessful) {
                        var chattingRoomList:List<ChattingRoomListDTO>;
                        chattingRoomList= response.body()!!;
                        chatting_room_list.layoutManager= LinearLayoutManager(requireContext(),
                            LinearLayoutManager.VERTICAL,false)
                        chatting_room_list.setHasFixedSize(true)
                        chatting_room_list.adapter= ChattingRoomAdapter(chattingRoomList)
                    }
                }

                override fun onFailure(call: Call<List<ChattingRoomListDTO>?>, t: Throwable) {

                }

            })
        }
        return view
    }
}