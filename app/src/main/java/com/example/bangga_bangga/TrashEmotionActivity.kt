package com.example.bangga_bangga

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class TrashEmotionActivity : AppCompatActivity() {
    private var keywordList: MutableList<String>? = null
    override fun onCreate(savedInstantState: Bundle?) {
        super.onCreate(savedInstantState)
        setContentView(R.layout.activity_transh_emotion)

        keywordList = intent.getStringArrayListExtra("keywordList")?.toMutableList()
        Log.d("keywordList", "Keyword List : $keywordList")

    }
}