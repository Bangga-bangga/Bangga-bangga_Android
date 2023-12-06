package com.example.bangga_bangga

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
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
                keywordTextView.setGravity(Gravity.CENTER)
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
}