package com.example.bangga_bangga

import android.os.Bundle
import android.os.PersistableBundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bangga_bangga.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

data class NicknameData(val nickname: String)

interface ApiService {
    @POST("your_endpoint_here") // 서버의 엔드포인트 URL을 입력해야 합니다.
    fun postNickname(@Body nickname:  NicknameData): Call<Void>
}

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val registerBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        registerBinding.duplicationCheckBtn.setOnClickListener{
            val nickname = registerBinding.nicknameInput.text.toString()
            if(nickname.isEmpty()) Toast.makeText(
                this@RegisterActivity,
                "닉네임을 입력해주세요.",
                Toast.LENGTH_SHORT
            ).show() else{

            }
        }
    }
    private fun sendNicknameToServer(nickname: NicknameData){
        val retrofit = Retrofit.Builder()
            .baseUrl("url") // 서버의 기본 URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.postNickname(nickname)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // 서버 요청 성공
                    // 원하는 작업 수행
                } else {
                    // 서버 요청 실패
                    // 실패 이유에 따라 처리
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // 네트워크 오류 등으로 인한 요청 실패 처리
            }
        })
    }
}