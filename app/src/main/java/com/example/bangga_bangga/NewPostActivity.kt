package com.example.bangga_bangga

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.bangga_bangga.api.NewPostApi
import com.example.bangga_bangga.model.NewPostModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewPostActivity : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var content: EditText
    private lateinit var postButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newpost)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        supportActionBar?.title = ""

        title = findViewById(R.id.title)
        content = findViewById(R.id.content)
        postButton = findViewById(R.id.post_button)

        // ==Post 버튼 클릭 시==
        postButton.setOnClickListener {
            val titleInput = title.text.toString()
            val contentInput = content.text.toString()

            // == 백엔드 통신 부분 ==
            val newPostApi = NewPostApi.create(this)
            val data = NewPostModel(titleInput, contentInput)

            // 유저 카테 고리 가져오기
            val prefCategory = getSharedPreferences("userCategory", Context.MODE_PRIVATE)
            val category = prefCategory.getString("category", null)
            Log.d("가져온 카테고리",category!!)
            newPostApi.newPost(category!! ,data).enqueue(object : Callback<Int> {
                override fun onResponse(
                    call: Call<Int>,
                    response: Response<Int>
                ) {
                    Log.d("게시글 작성 통신 성공", response.body().toString())

                    val statusCode = response.raw().code()

                    when (statusCode) {
                        201 -> {
                            val postId = response.body()
                            Log.d("게시글 아이디", "$postId")

                            Toast.makeText(this@NewPostActivity, "post 성공", Toast.LENGTH_LONG).show()
                            // 일단은 메인페이지로 가도록 수정
                            convertToMainActivity()
                        }
                        else -> {
                            // 기타 상태 코드에 대한 처리 추가
                            Log.d("게시글 작성 실패", "알 수 없는 상태 코드: $statusCode")
                            Log.d("오류","통신 오류\n에러 메시지: ${response.body()}" )
                            runOnUiThread {
                                Toast.makeText(
                                    this@NewPostActivity,
                                    "게시글 작성 실패 : 알 수 없는 상태 코드 - $statusCode",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    // 실패
                    Log.d("게시글 작성 통신 실패",t.message.toString())
                    Log.d("게시글 작성 실패","fail")
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun convertToMainActivity() {   /**임시로 메인 엑티비티로 해둠**/
    val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}