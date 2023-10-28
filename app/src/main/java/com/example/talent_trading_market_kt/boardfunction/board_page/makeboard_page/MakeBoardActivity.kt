package com.example.talent_trading_market_kt.boardfunction.board_page.makeboard_page


import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.talent_trading_market_kt.R
import com.example.talent_trading_market_kt.boardfunction.api.BoardFunctionApi
import com.example.talent_trading_market_kt.boardfunction.postsearch.SearchBoardAdapter
import com.example.talent_trading_market_kt.dto.boardfunctiondto.PostBoardDTO
import com.example.talent_trading_market_kt.retrofit.RetrofitConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        val progressBar = ProgressBar(this)
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
            var progressDialog: AlertDialog? = null
            progressDialog = AlertDialog.Builder(this)
                .setView(progressBar)
                .setTitle("게시글 작성 중...")
                .setCancelable(false)
                .create()

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    var postImage: ByteArray? = null
                    val postname = postName.text.toString()
                    val content = content.text.toString()
                    val price = makeboard_price.text.toString()
                    val makeprice = price.toLong()
                    val postBoardDTO = PostBoardDTO()
                    postBoardDTO.writerNickname = "writer"
                    postBoardDTO.postName = postname
                    postBoardDTO.content = content
                    postBoardDTO.price = makeprice

                    if (board_photo1 != null) {
                        val stream = ByteArrayOutputStream()
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                        postImage = stream.toByteArray()
                        postBoardDTO.image = postImage
                        stream.close()
                    }

                    withContext(Dispatchers.Main) {
                        progressDialog?.show()
                    }

                    if (service != null) {
                        val response = service.make_board(postBoardDTO).execute()
                        if (response.isSuccessful) {
                            withContext(Dispatchers.Main) {
                                progressDialog?.dismiss()
                                // 게시물 작성 완료 시 다음 작업 수행
                                finish()
                            }
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        progressDialog?.dismiss()
                        e.printStackTrace()
                        // 에러 처리
                    }
                }
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
                runOnUiThread {
                    Glide.with(this)
                        .load(uri) // 이미지
                        .into(board_photo1) // 보여줄 위치
                }
            }
        }
}