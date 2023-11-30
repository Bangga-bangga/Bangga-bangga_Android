package com.example.bangga_bangga

import android.os.Bundle
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

        inputKeywordBtn.setOnClickListener {
            val keyword = editTextEmotionKeyword.text.toString().trim()

            if (keyword.isNotEmpty()) {
                keywordList.add(keyword)

                val keywordTextView = TextView(this, null, R.style.keywordList)
                keywordTextView.text = keyword
                keywordListLayout.addView(keywordTextView)
            }

            editTextEmotionKeyword.text.clear()
        }

    }
}