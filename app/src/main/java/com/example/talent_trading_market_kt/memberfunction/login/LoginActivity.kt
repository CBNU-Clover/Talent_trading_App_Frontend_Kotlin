package com.example.talent_trading_market_kt.memberfunction.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.talent_trading_market_kt.MainActivity
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.dto.memberfunctiondto.LoginDTO
import com.example.talent_trading_market_kt.memberfunction.register.RegisterMember
import com.example.talent_trading_market_kt.memberfunction.api.MemberFunctionApi
import com.example.talent_trading_market_kt.retrofit.App
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import retrofit2.*


class LoginActivity : AppCompatActivity() {

    lateinit var id:EditText
    lateinit var password:EditText
    lateinit var button:Button
    lateinit var register_button:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        id=findViewById(R.id.login_Nickname)
        password=findViewById(R.id.login_passWord)
        button=findViewById(R.id.Login)
        register_button=findViewById(R.id.register_btn)
        id.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때 기본 색상으로 변경
                id.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.black))
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때 기본 색상으로 변경
                password.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.black))
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        register_button.setOnClickListener {
            val intent= Intent(this, RegisterMember::class.java)
            startActivity(intent)
        }
        val service = RetrofitConnection.getInstance().create(MemberFunctionApi::class.java)
        button.setOnClickListener {
            val idStr=id.text.toString()
            val pwStr=password.text.toString()
            if((id.length()==0)&&(password.length()!=0))
            {
                Toast.makeText(this@LoginActivity, "닉네임을 입력해주세요!!", Toast.LENGTH_SHORT).show()
            }
            else if((id.length()!=0)&&(password.length()==0))
            {
                Toast.makeText(this@LoginActivity, "비밀번호를 입력해주세요!!", Toast.LENGTH_SHORT).show()
            }
            else if((id.length()==0)&&(password.length()==0))
            {
                Toast.makeText(this@LoginActivity, "입력해주세요!!", Toast.LENGTH_SHORT).show()
            }
            else if((id.length()!=0)&&(password.length()!=0))
            {
                val loginDTO = LoginDTO()
                loginDTO.nickname = idStr
                loginDTO.passWord = pwStr
                if (service != null) {
                    service.login(loginDTO).enqueue(object : Callback<String?> {
                        override fun onResponse(call: Call<String?>, response: Response<String?>) {
                            if (response.isSuccessful) {
                                val message: String?
                                message = response.body().toString()
                                if(message=="!회원 정보가 없습니다!")
                                {
                                    Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                                    id.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.red))
                                }
                                else if(message=="!비밀번호가 틀립니다!")
                                {
                                    Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                                    password.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.red))
                                }
                                else
                                {
                                    App.prefs.token=message
                                    App.prefs.nickname=loginDTO.nickname

                                    Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    finishAffinity()
                                    startActivity(intent)
                                }
                            }
                        }

                        override fun onFailure(call: Call<String?>, t: Throwable) {
                            Toast.makeText(this@LoginActivity, "다시 로그인 버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }
        }
    }
}



