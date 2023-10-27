package com.example.talent_trading_market_kt.boardfunction.board_page.makeboard_page


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.boardfunction.postsearch.SearchBoardAdapter
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostBoardDTO
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class MakeBoardActivity : AppCompatActivity() {
    lateinit var postName: EditText
    lateinit var content: EditText
    lateinit var write_bt:Button
    lateinit var makeboard_price:EditText
    lateinit var backbt_makeboard:ImageButton
    lateinit var searchBoardAdapter: SearchBoardAdapter
    lateinit var add_board_photo_bt:ImageButton
    lateinit var board_photo1:ImageView
    var bitmap :Bitmap?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.board_write_page)
        postName = findViewById(R.id.title)
        content = findViewById(R.id.content)
        write_bt = findViewById(R.id.make_content)
        makeboard_price=findViewById(R.id.makeboard_price)
        backbt_makeboard=findViewById(R.id.backbt_makeboard)
        add_board_photo_bt=findViewById(R.id.add_board_photo)
        board_photo1=findViewById(R.id.board_photo1)

        val service = RetrofitConnection.getInstance().create(BoardFunctionApi::class.java)
        backbt_makeboard.setOnClickListener {
            finish()
        }
        add_board_photo_bt.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            activityResult.launch(intent)

        }
        write_bt.setOnClickListener {
            var postImage:ByteArray?=null
            val postname=postName.text.toString()
            val content=content.text.toString()
            val price=makeboard_price.text.toString()
            val makeprice=price.toLong()
            val postBoardDTO= PostBoardDTO()
            postBoardDTO.writerNickname="writer"
            postBoardDTO.postName=postname
            postBoardDTO.content=content
            postBoardDTO.price=makeprice
           if(board_photo1!=null)
            {
                //val bitmap=(board_photo1.drawable as BitmapDrawable).bitmap
                val stream=ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.JPEG,100,stream)

                postImage=stream.toByteArray()
                postBoardDTO.image=postImage
                stream.close()
            }
            if(service!=null)
            {
                service.make_board(postBoardDTO).enqueue(object : Callback<Long?> {
                    override fun onResponse(call: Call<Long?>, response: Response<Long?>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@MakeBoardActivity, "게시물 쓰기 완료", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<Long?>, t: Throwable) {
                        Toast.makeText(this@MakeBoardActivity, "다시 버튼을 눌러주세요", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
            }
        }
    }
    private val activityResult:ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult())
        {
            // 결과 코드 OK , 결과값 null 아니면
            if(it.resultCode==RESULT_OK&&it.data!=null)
            {
                //값 담기
                val uri=it.data!!.data
                bitmap =
                    MediaStore.Images.Media.getBitmap(applicationContext.getContentResolver(), uri);
                //화면에 보여주기
                Glide.with(this)
                    .load(uri) // 이미지
                    .into(board_photo1) // 보여줄 위치
            }
        }
}