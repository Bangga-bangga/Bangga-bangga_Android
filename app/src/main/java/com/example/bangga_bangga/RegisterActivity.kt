package com.example.bangga_bangga

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.bangga_bangga.databinding.ActivityRegisterBinding
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.regex.Pattern

data class NicknameValidationResponse(
    val result: String?,
    val code: Int?,
    val message: String?,
    val details: String?
)
data class RegisterRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("age") val age: Int
)
data class RegisterResponse(
    val result: String?,
    val type: String?,
    val title: String?,
    val status: Int?,
    val detail: String?,
    val instance: String?
)

interface NicknameCheckService {
    @GET("/sign-up/exists") // 서버의 엔드포인트 URL을 입력해야 합니다.
    fun checkNickname(
        @Query("nickname") nickname: String
    ): Call<NicknameValidationResponse>
}

interface RegisterService {
    @POST("/sign-up") // 서버의 엔드포인트 URL을 입력해야 합니다.
    fun signUp(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>
}

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        var nicknameValidation = 0
        var passwordValidation = 0
        var emailValidation = 0
        val emailForm = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        /** 툴바 생성 코드**/
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        supportActionBar?.title = ""

        fun checkEmail():Boolean{
            val email = registerBinding.emailInput.text.toString().trim()
            var emailCheck = registerBinding.emailVaildationCheckResult
            val p = Pattern.matches(emailForm, email) // 서로 패턴이 맞는지 검사
            if (p) {
                emailCheck.setTextColor(Color.rgb(52, 107, 235))
                emailCheck.text = "* 유효한 이메일 형식입니다."
                emailCheck.visibility = View.VISIBLE
                emailValidation = 1
                return true
            } else {
                emailCheck.setTextColor(Color.rgb(250,80,42))
                emailCheck.text = "* 정상적인 이메일 형식이 아닙니다."
                emailCheck.visibility = View.VISIBLE
                emailValidation - 0
                return false
            }
        }

        // Retrofit 객체 생성
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-13-125-135-255.ap-northeast-2.compute.amazonaws.com:8080/") // 서버 기본 URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // API 서비스 생성
        val nicknameCheckService = retrofit.create(NicknameCheckService::class.java)
        val registerService = retrofit.create(RegisterService::class.java)


        registerBinding.duplicationCheckBtn.setOnClickListener{
            val nickname = registerBinding.nicknameInput.text.toString()
            var nicknameCheck = registerBinding.duplicationCheckResultText
            if(nickname.isEmpty()) Toast.makeText(
                this@RegisterActivity,
                "닉네임을 입력해주세요.",
                Toast.LENGTH_SHORT
            ).show() else{
                // API 요청 보내기
                nicknameCheckService.checkNickname(nickname).enqueue(object : Callback<NicknameValidationResponse> {
                    override fun onResponse(
                        call: Call<NicknameValidationResponse>,
                        response: Response<NicknameValidationResponse>
                    ) {
                        if (response.isSuccessful) {
                            val nicknameResponse = response.body()
                            if (nicknameResponse != null) {
                                if (response.code() == 200) {
                                    nicknameCheck.text = "* 사용 가능한 닉네임입니다."
                                    nicknameCheck.visibility = View.VISIBLE
                                    nicknameCheck.setTextColor(Color.rgb(52, 107, 235))
                                    nicknameValidation = 1
                                } else {

                                }
                            }
                        } else {
                            if (response.code() == 400){
                                nicknameCheck.text = "* 이미 존재하는 닉네임입니다."
                                nicknameCheck.visibility = View.VISIBLE
                                nicknameCheck.setTextColor(Color.rgb(250,80,42))
                                nicknameValidation = 0
                            }
                        }
                    }
                    override fun onFailure(call: Call<NicknameValidationResponse>, t: Throwable) {
                        // 네트워크 오류 등으로 인한 요청 실패 처리
                        Toast.makeText(this@RegisterActivity, "닉네임 중복 검사 실패", Toast.LENGTH_SHORT)
                    }
                })
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
            if(password.length < 8){
                passwordCheck.setTextColor(Color.rgb(250,80,42))
                passwordCheck.text = "* 비밀번호를 8자리 이상 입력해주세요."
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
            if (email != "" && emailValidation == 1){
                emailCheck.visibility = View.INVISIBLE
            }
            if (password != "" && password.length > 7){
                passwordCheck.visibility = View.INVISIBLE
            }
            if (repassword != ""){
                repasswordCheck.visibility = View.INVISIBLE
            }
            if (age != ""){
                ageCheck.visibility = View.INVISIBLE
            }
            if (password != repassword){
                passwordValidation = 0
                repasswordCheck.setTextColor(Color.rgb(250,80,42))
                repasswordCheck.text = "* 비밀번호가 일치하지 않습니다."
                repasswordCheck.visibility = View.VISIBLE
            }

            if (nickname != "" && email != "" && password != "" && repassword != "" && age != "" && nicknameValidation == 1 && passwordValidation == 1 && emailValidation == 1){
                // json형식으로 서버에 회원가입 api 요청하기
                val registerRequest = RegisterRequest(email,password,nickname,age.toInt())
                val call = registerService.signUp(registerRequest)
                call.enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        if (response.isSuccessful) {
                            val signUpResponse = response.body()
                            if (response.code() == 200) {
                                val intent =
                                    Intent(this@RegisterActivity, StartActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this@RegisterActivity, "회원가입 성공", Toast.LENGTH_SHORT)
                                    .show()
                                // 성공적으로 회원가입된 경우
                            }
                        } else {
                            if (response.code() == 400) {
                                Toast.makeText(this@RegisterActivity, "회원가입 입력 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        // 네트워크 오류 등으로 인한 요청 실패 처리
                        Toast.makeText(this@RegisterActivity, "회원가입 실패", Toast.LENGTH_SHORT)
                    }
                })
            } else {
                if(nicknameValidation == 0){
                    nicknameCheck.setTextColor(Color.rgb(250,80,42))
                    nicknameCheck.text = "* 닉네임 중복을 확인해주세요."
                    nicknameCheck.visibility = View.VISIBLE
                }
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
                var passwordCheck = registerBinding.passwordVaildationCheckResult
                if(password.length < 8){
                    passwordCheck.setTextColor(Color.rgb(250,80,42))
                    passwordCheck.text = "* 비밀번호를 8자리 이상 입력해주세요."
                    passwordCheck.visibility = View.VISIBLE
                } else {
                    passwordCheck.visibility = View.INVISIBLE
                }
                if (password != "" && repassword != ""){
                    if(password != repassword){
                        passwordValidation = 0
                        repasswordCheck.setTextColor(Color.rgb(250,80,42))
                        repasswordCheck.text = "* 비밀번호가 일치하지 않습니다."
                        repasswordCheck.visibility = View.VISIBLE
                    } else {
                        repasswordCheck.setTextColor(Color.rgb(52, 107, 235))
                        passwordValidation = 1
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
}