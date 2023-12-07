package com.example.bangga_bangga

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.bangga_bangga.api.Api
import com.example.bangga_bangga.model.LoginBackendResponse
import com.example.bangga_bangga.model.UserModel
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity  : AppCompatActivity()  {

    private lateinit var login_email: EditText
    private lateinit var login_password: EditText
    private lateinit var login_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        supportActionBar?.title = ""

        login_email = findViewById(R.id.login_email)
        login_password = findViewById(R.id.login_password)
        login_button = findViewById(R.id.login_button)
        // ==로그인 버튼 클릭 시==
        login_button.setOnClickListener {
            val username = login_email.text.toString().trim()//trim : 문자열 공백제거
            val password = login_password.text.toString().trim()

            // == 백엔드 통신 부분 ==
            val api = Api.create()
            val data = UserModel(username, password)

            api.userLogin(data).enqueue(object : Callback<LoginBackendResponse> {
                override fun onResponse(
                    call: Call<LoginBackendResponse>,
                    response: Response<LoginBackendResponse>
                ) {
                    Log.d("로그인 통신 성공",response.toString())
                    Log.d("로그인 통신 성공", response.body().toString())

                    val statusCode = response.raw().code()

                    when (statusCode) {
                        200 -> {
                            // Authorization 토큰 값 가져오기
                            val authToken = response.headers()["Authorization"]
                            saveAuthToken(authToken)
                            // 획득한 토큰 출력
                            Log.d("획득한 토큰", authToken ?: "토큰이 없습니다.")

                            Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_LONG).show()
                            convertToMainActivity()
                        }
                        401 -> {
                            Log.d("로그인 실패", "아이디나 비번이 올바르지 않습니다")
                            runOnUiThread {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "로그인 실패 : 아이디나 비번이 올바르지 않습니다",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        500 -> {
                            Log.d("로그인 실패", "서버 오류")
                            runOnUiThread {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "로그인 실패 : 서버 오류\n에러 메시지: 아이디가 올바르지 않습니다.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        else -> {
                            // 기타 상태 코드에 대한 처리 추가
                            Log.d("로그인 실패", "알 수 없는 상태 코드: $statusCode")
                            runOnUiThread {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "로그인 실패 : 알 수 없는 상태 코드 - $statusCode",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    }
                }

                override fun onFailure(call: Call<LoginBackendResponse>, t: Throwable) {
                    // 실패
                    Log.d("로그인 통신 실패",t.message.toString())
                    Log.d("로그인 통신 실패","fail")
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

    //Device File Explorer 에서 data/data/[package 이름]/shared_prefs/ 확인
    // Shared Preference에 Authorization 토큰 저장 함수
    private fun saveAuthToken(token: String?) {
        val prefToken = getSharedPreferences("userToken", MODE_PRIVATE)
        val editToken = prefToken.edit()
        editToken.putString("token", token)
        editToken.apply()
    }
}

