package com.example.bangga_bangga

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.text.isDigitsOnly
import com.example.bangga_bangga.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.regex.Pattern

//data class NicknameData(val nickname: String)
//
//interface ApiService {
//    @POST("your_endpoint_here") // 서버의 엔드포인트 URL을 입력해야 합니다.
//    fun postNickname(@Body nickname:  NicknameData): Call<Void>
//}

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        var validate = 0
        var passwordValidate = 0
        val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        /** 툴바 생성 코드**/
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        supportActionBar?.title = ""

        fun checkEmail():Boolean{
            val email = registerBinding.emailInput.text.toString().trim()
            var emailCheck = registerBinding.emailVaildationCheckResult
            val p = Pattern.matches(emailValidation, email) // 서로 패턴이 맞는지 검사
            if (p) {
                emailCheck.setTextColor(Color.rgb(52, 107, 235))
                emailCheck.text = "* 유효한 이메일 형식입니다."
                emailCheck.visibility = View.VISIBLE
                return true
            } else {
                emailCheck.setTextColor(Color.rgb(250,80,42))
                emailCheck.text = "* 정상적인 이메일 형식이 아닙니다."
                emailCheck.visibility = View.VISIBLE
                return false
            }
        }

        registerBinding.duplicationCheckBtn.setOnClickListener{
            val nickname = registerBinding.nicknameInput.text.toString()
            if(nickname.isEmpty()) Toast.makeText(
                this@RegisterActivity,
                "닉네임을 입력해주세요.",
                Toast.LENGTH_SHORT
            ).show() else{
                // 입력된 비밀번호를 파라미터에 담아서 서버에 중복 검사 api 요청하기
                // 성공시 -> "사용 가능한 닉네임입니다" 띄우기 + vaildation을 1로 바꾸기
                // 실패시 -> "사용 불가능한 닉네임입니다" 띄우기
            }
        }

        registerBinding.registerBtn.setOnClickListener{
            val nickname = registerBinding.nicknameInput.text.toString()
            val email = registerBinding.emailInput.text.toString()
            val password = registerBinding.passwordInput.text.toString()
            val repassword = registerBinding.passwordCheckInput.text.toString()
            val age = registerBinding.ageInput.text.toString()
            var nicknameCheck = registerBinding.duplicationCheckResultText
            var emailCheck = registerBinding.emailVaildationCheckResult
            var passwordCheck = registerBinding.passwordVaildationCheckResult
            var ageCheck = registerBinding.ageVaildationCheckResult
            var repasswordCheck = registerBinding.repasswordVaildationCheckResult

            if(nickname == ""){   // 닉네임 입력이 없을 때
                nicknameCheck.setTextColor(Color.rgb(250,80,42))
                nicknameCheck.text = "* 닉네임을 입력해주세요."
                nicknameCheck.visibility = View.VISIBLE
            }
            if(email == ""){    // 이메일 입력이 없을 때
                emailCheck.setTextColor(Color.rgb(250,80,42))
                emailCheck.text = "* 이메일을 입력해주세요."
                emailCheck.visibility = View.VISIBLE
            }
            if(password == ""){
                passwordCheck.setTextColor(Color.rgb(250,80,42))
                passwordCheck.text = "* 비밀번호를 입력해주세요."
                passwordCheck.visibility = View.VISIBLE
            }
            if(repassword == ""){
                repasswordCheck.setTextColor(Color.rgb(250,80,42))
                repasswordCheck.text = "* 비밀번호를 재입력해주세요."
                repasswordCheck.visibility = View.VISIBLE
            }
            if(age == ""){
                ageCheck.setTextColor(Color.rgb(250,80,42))
                ageCheck.text = "* 나이를 입력해주세요."
                ageCheck.visibility = View.VISIBLE
            }
            if (nickname != ""){
                nicknameCheck.visibility = View.INVISIBLE
            }
            if (email != ""){
                emailCheck.visibility = View.INVISIBLE
            }
            if (password != ""){
                passwordCheck.visibility = View.INVISIBLE
            }
//            if (repassword != ""){
//                repasswordCheck.visibility = View.INVISIBLE
//            }
            if (age != ""){
                ageCheck.visibility = View.INVISIBLE
            }

            if (nickname != "" && email != "" && password != "" && repassword != "" && age != "" && validate == 1 && passwordValidate == 1){
                // json형식으로 서버에 회원가입 api 요청하기
            }
        }
        registerBinding.passwordCheckInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                TODO("Not yet implemented")
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = registerBinding.passwordInput.text.toString()
                val repassword = registerBinding.passwordCheckInput.text.toString()
                var repasswordCheck = registerBinding.repasswordVaildationCheckResult
                if (password != "" && repassword != ""){
                    if(password != repassword){
                        passwordValidate = 0
                        repasswordCheck.setTextColor(Color.rgb(250,80,42))
                        repasswordCheck.text = "* 비밀번호가 일치하지 않습니다."
                        repasswordCheck.visibility = View.VISIBLE
                    } else {
                        repasswordCheck.setTextColor(Color.rgb(52, 107, 235))
                        passwordValidate = 1
                        repasswordCheck.text = "* 비밀번호가 일치합니다."
                        repasswordCheck.visibility = View.VISIBLE
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {
//                TODO("Not yet implemented")
            }
        })

        registerBinding.emailInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // text가 변경된 후 호출
                // s에는 변경 후의 문자열이 담겨 있다.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // text가 변경되기 전 호출
                // s에는 변경 전 문자열이 담겨 있다.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // text가 바뀔 때마다 호출된다.
                // 우린 이 함수를 사용한다.
                checkEmail()
            }
        })
        registerBinding.ageInput.addTextChangedListener(object : TextWatcher {
            val age = registerBinding.ageInput.text.toString()
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(age.length > 2){

                }
            }
        })

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
//    private fun sendNicknameToServer(nickname: NicknameData){
//        val retrofit = Retrofit.Builder()
//            .baseUrl("url") // 서버의 기본 URL
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val apiService = retrofit.create(ApiService::class.java)
//        val call = apiService.postNickname(nickname)
//        call.enqueue(object : Callback<Void> {
//            override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                if (response.isSuccessful) {
//                    // 서버 요청 성공
//                    // 원하는 작업 수행
//                } else {
//                    // 서버 요청 실패
//                    // 실패 이유에 따라 처리
//                }
//            }
//
//            override fun onFailure(call: Call<Void>, t: Throwable) {
//                // 네트워크 오류 등으로 인한 요청 실패 처리
//            }
//        })
//    }
}