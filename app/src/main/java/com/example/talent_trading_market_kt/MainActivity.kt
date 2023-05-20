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
        //setFrag(0)
       /* bottomNavigationView.setOnItemReselectedListener {
            when(it.itemId)
            {

            }
        }
*/
        //밑에 네비게이션 bar로 표시
        //1. 랭킹 눌렀을때
        //2. 메뉴 눌렀을때-> activity_board.xml로 이동 , 펜 같은 거 누르면 글쓰기 기능 가능
        //3. 마이페이지 눌렀을때 -> 마이페이지 누르면 마이페이지로 이동 ( 5/20일 )

        //4. 채팅 눌렀을때
        //5. 거래 기록 눌렀을때

        //button=findViewById(R.id.bt)
        //board_bt=findViewById(R.id.board_bt)

      /*  bt.setOnClickListener {

            val intent=Intent(this, LoginActivity::class.java)
                startActivity(intent)

        }*/
        /*board_bt.setOnClickListener {

           val intent=Intent(this, BoardMainActivity::class.java)
            startActivity(intent)
        }*/

    }

    /*private fun setFrag(fragNum:Int) {
        val ft=supportFragmentManager.beginTransaction()
        when(fragNum)
        {
            0->{
                ft.replace(R.id.main_frame,Fragment1_Home()).commit()
            }
            1->{
                ft.replace(R.id.main_frame,Fragment2_Menu()).commit()
            }
            2-> {
                ft.replace(R.id.main_frame,Fragment3_MyPage()).commit()
            }
            3->{
                ft.replace(R.id.main_frame,Fragment4_Chat()).commit()
            }
            4->{
                ft.replace(R.id.main_frame,Fragment5_Trading_History()).commit()
            }
        }
    }*/
    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_frame,fragment)
        fragmentTransaction.commit()
    }
}