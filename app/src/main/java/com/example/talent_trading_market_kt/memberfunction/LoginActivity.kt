package com.example.talent_trading_market_kt.memberfunction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.talent_trading_market_kt.MainActivity
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.dto.memberfunctiondto.LoginDTO
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
        setContentView(R.layout.activity_login)

        id=findViewById(R.id.login_Nickname)
        password=findViewById(R.id.login_passWord)
        button=findViewById(R.id.Login)
        register_button=findViewById(R.id.register_btn)

        register_button.setOnClickListener {
            val intent= Intent(this, RegisterMember::class.java)
            startActivity(intent)
        }
        val service = RetrofitConnection.getInstance().create(MemberFunctionApi::class.java)
        button.setOnClickListener {
            val idStr=id.text.toString()
            val pwStr=password.text.toString()
            val loginDTO = LoginDTO()
            loginDTO.nickname = idStr
            loginDTO.passWord = pwStr
            if (service != null) {
                service.login(loginDTO).enqueue(object : Callback<String?> {
                    override fun onResponse(call: Call<String?>, response: Response<String?>) {
                        if (response.isSuccessful) {
                            val message: String?
                            message = response.body().toString()
                            // 토큰을 저장한다.
                            App.prefs.token=message
                            Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            finishAffinity()
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
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



