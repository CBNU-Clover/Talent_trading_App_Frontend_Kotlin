package com.example.talent_trading_market_kt.memberfunction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.dto.EmailCheckDTO
import com.example.talent_trading_market_kt.dto.MemberJoinDTO
import com.example.talent_trading_market_kt.dto.NickCheckDTO
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Level
import java.util.logging.Logger

class RegisterMember : AppCompatActivity() {
    lateinit var nickname: EditText
    lateinit var email: EditText
    lateinit var name: EditText
    lateinit var password: EditText
    lateinit var phone_number: EditText
    lateinit var nicknamebt: Button
    lateinit var emailbt: Button
    lateinit var register_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nickname = findViewById(R.id.nickname)
        nicknamebt = findViewById(R.id.nickcheckButton)
        email = findViewById(R.id.email)
        emailbt = findViewById(R.id.emailcheckButton)
        name = findViewById(R.id.name)
        password = findViewById(R.id.password)
        phone_number = findViewById(R.id.phone_number)
        register_button = findViewById(R.id.registerButton)

        val service = RetrofitConnection.getInstance().create(MemberFunctionApi::class.java)

        //닉네임 중복 체크
        nicknamebt.setOnClickListener {
            val nickname=nickname.text.toString()
            val nickCheckDTO=NickCheckDTO()
            nickCheckDTO.nickname=nickname
            if(service!=null)
            {
                service.check_Nick(nickCheckDTO).enqueue(object : Callback<String?> {
                    override fun onResponse(call: Call<String?>, response: Response<String?>) {
                        if (response.isSuccessful) {
                            val message: String? = response.body()
                            Toast.makeText(this@RegisterMember, message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<String?>, t: Throwable) {
                        Toast.makeText(this@RegisterMember, "다시 입력해주세요", Toast.LENGTH_SHORT)
                            .show()
                        Logger.getLogger(RegisterMember::class.java.getName())
                            .log(Level.SEVERE, "Error occured", t)
                    }

                })
            }
        }

        //이메일 중복체크
        emailbt.setOnClickListener {
            val email=email.text.toString()
            val emailCheckDTO=EmailCheckDTO()
            emailCheckDTO.email=email
            if(service!=null)
            {
                    service.check_email(emailCheckDTO).enqueue(object : Callback<String?> {
                        override fun onResponse(call: Call<String?>, response: Response<String?>) {
                            if (response.isSuccessful) {
                                val message: String? = response.body()
                                Toast.makeText(this@RegisterMember, message, Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<String?>, t: Throwable) {
                            Toast.makeText(this@RegisterMember, "다시 입력해주세요", Toast.LENGTH_SHORT)
                                .show()
                            Logger.getLogger(RegisterMember::class.java.getName())
                                .log(Level.SEVERE, "Error occured", t)
                        }

                    })
            }

        }
        register_button.setOnClickListener {
            val nickname = nickname.text.toString()
            val email = email.text.toString()
            val name = name.text.toString()
            val phone_number = phone_number.text.toString()
            val password = password.text.toString()
            val memberjoinDTO = MemberJoinDTO()
            memberjoinDTO.name = name
            memberjoinDTO.nickname = nickname
            memberjoinDTO.email = email
            memberjoinDTO.phoneNumber = phone_number
            memberjoinDTO.passWord = password
            if (service != null) {
                service.join(memberjoinDTO).enqueue(object : Callback<String?> {
                    override fun onResponse(call: Call<String?>, response: Response<String?>) {
                        if (response.isSuccessful) {
                            val message: String? = response.body()
                            Toast.makeText(this@RegisterMember, message, Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<String?>, t: Throwable) {
                        Toast.makeText(this@RegisterMember, "회원가입이 실패했습니다!!", Toast.LENGTH_SHORT)
                            .show()
                        Logger.getLogger(RegisterMember::class.java.getName())
                            .log(Level.SEVERE, "Error occured", t)
                    }

                })


            }
        }
    }
}