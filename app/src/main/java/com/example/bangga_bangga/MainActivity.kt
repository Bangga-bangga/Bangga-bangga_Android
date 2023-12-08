package com.example.bangga_bangga

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /** 툴바 생성 코드**/
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        supportActionBar?.title = ""
        /** 툴바 코드 끗 아래 onOptionsItemSelected함수 또한 자신의 파일에 붙여넣기 **/

        val convertPageBtn = findViewById<Button>(R.id.convert_page_btn)
        val loginPageBtn = findViewById<Button>(R.id.login_page_btn)
        val startPageBtn = findViewById<Button>(R.id.start_page_btn)
        val newpostPageBtn = findViewById<Button>(R.id.newpost_page_btn)

        fun convertToTrashKeywordActivity() {
            val intent = Intent(this, TrashKeywordActivity::class.java)
            startActivity(intent)
        }

        convertPageBtn.setOnClickListener {
            convertToTrashKeywordActivity()
        }

        fun convertToLoginActivity() {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        loginPageBtn.setOnClickListener {
            convertToLoginActivity()
        }

        fun convertToStartActivity() {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        }

        startPageBtn.setOnClickListener {
            convertToStartActivity()
        }

        fun convertToNewpostActivity() {
            val intent = Intent(this, NewPostActivity::class.java)
            startActivity(intent)
        }

        newpostPageBtn.setOnClickListener {
            convertToNewpostActivity()
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

}
