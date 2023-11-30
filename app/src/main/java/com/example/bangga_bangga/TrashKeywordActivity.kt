package com.example.bangga_bangga

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class TrashKeywordActivity : AppCompatActivity() {
    private val keywordList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trash_keyword)

        val editTextEmotionKeyword = findViewById<EditText>(R.id.editText_emotion_keyword)
        val inputKeywordBtn = findViewById<Button>(R.id.input_keyword_btn)

        inputKeywordBtn.setOnClickListener {
            val keyword = editTextEmotionKeyword.text.toString().trim()

            if (keyword.isNotEmpty()) {
                keywordList.add(keyword)

            }

            editTextEmotionKeyword.text.clear()
        }

    }
}