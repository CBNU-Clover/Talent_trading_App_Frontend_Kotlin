package com.example.talent_trading_market_kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.talent_trading_market_kt.databinding.MainBinding
import com.example.talent_trading_market_kt.fragment.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainBinding
    //lateinit var button: Button
    //lateinit var board_bt:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=MainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Fragment1_Home())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home->replaceFragment(Fragment1_Home())
                R.id.menu->replaceFragment(Fragment2_Menu())
                R.id.go_mypage->replaceFragment(Fragment3_MyPage())
                R.id.ranking->replaceFragment(Fragment4_Ranking())
                R.id.trading_history->replaceFragment(Fragment5_Trading_History())

                else->{

                }


            }
            true
        }
        //밑에 네비게이션 bar로 표시
        //1. 랭킹 눌렀을때
        //2. 메뉴 눌렀을때-> activity_board.xml로 이동 , 펜 같은 거 누르면 글쓰기 기능 가능
        //3. 마이페이지 눌렀을때 -> 마이페이지 누르면 마이페이지로 이동 ( 5/20일 )

        //4. 채팅 눌렀을때
        //5. 거래 기록 눌렀을때
    }

    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_frame,fragment)
        fragmentTransaction.commit()
    }
}