package com.example.talent_trading_market_kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.talent_trading_market_kt.databinding.NavigationBottonBarBinding
import com.example.talent_trading_market_kt.fragment.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: NavigationBottonBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= NavigationBottonBarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ranking->replaceFragment(Fragment1_Ranking())
                R.id.board_menu->replaceFragment(Fragment2_Board())
                R.id.home->replaceFragment(Fragment3_Home())
                R.id.trade_history->replaceFragment(Fragment4_Trading_History())
                R.id.chatting->replaceFragment(Fragment5_Chatting())
                else->{
                }
            }
            true
        }
        binding.bottomNavigationView.selectedItemId = R.id.home
    }

    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_frame,fragment)
        fragmentTransaction.commit()
    }
}