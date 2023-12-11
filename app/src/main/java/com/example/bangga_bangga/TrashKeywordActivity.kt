package com.example.bangga_bangga

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class TrashKeywordActivity : AppCompatActivity() {
    private val keywordList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trash_keyword)

//        /** 툴바 생성 **/
//        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
//        supportActionBar?.title = ""
//        /** 툴바 코드 끝 아래 onOptionsItemSelected또한 복사**/

        val editTextEmotionKeyword = findViewById<EditText>(R.id.editText_emotion_keyword)
        val inputKeywordBtn = findViewById<Button>(R.id.input_keyword_btn)
        val keywordListLayout = findViewById<LinearLayout>(R.id.keyword_list_layout)

        val goEmptyCanBtn = findViewById<Button>(R.id.go_empty_can)

        inputKeywordBtn.setOnClickListener {
            val keyword = editTextEmotionKeyword.text.toString().trim()

            if (keyword.isNotEmpty()) {
                keywordList.add(keyword)

                val keywordTextView = TextView(this)
                keywordTextView.text = keyword
                keywordTextView.setTextColor(Color.parseColor("#2A2500"))
                keywordTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30f)
                keywordTextView.gravity = Gravity.CENTER
                keywordTextView.setPadding(0, 15, 0, 15)
                keywordListLayout.addView(keywordTextView)

            }

            editTextEmotionKeyword.text.clear()
        }

        fun convertToTrashEmotionActivity() {
            var intent = Intent(this, TrashEmotionActivity::class.java)
            intent.putStringArrayListExtra("keywordList", ArrayList(keywordList))
            startActivity(intent)
        }

        goEmptyCanBtn.setOnClickListener {
            convertToTrashEmotionActivity()
        }

    }

//    /** 툴바**/
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//                //toolbar의 back키 눌렀을 때 동작
//                // 액티비티 이동
//                finish()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
}