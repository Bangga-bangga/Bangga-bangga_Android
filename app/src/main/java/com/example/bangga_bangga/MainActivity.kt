package com.example.bangga_bangga

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val convertPageBtn = findViewById<Button>(R.id.convert_page_btn)

        fun convertToTrashKeywordActivity() {
            val intent = Intent(this, TrashKeywordActivity::class.java)
            startActivity(intent)
        }

        convertPageBtn.setOnClickListener {
            convertToTrashKeywordActivity()
        }
    }
}
