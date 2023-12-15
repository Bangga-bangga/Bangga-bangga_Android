package com.example.bangga_bangga

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class StartActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var signInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        supportActionBar?.title = ""

        loginButton = findViewById<Button>(R.id.login_button)
        signInButton = findViewById<Button>(R.id.sign_in_button)

        loginButton.setOnClickListener { convertToLoginActivity() }
        signInButton.setOnClickListener { convertToRegisterActivity() }
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

    fun convertToRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun convertToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}