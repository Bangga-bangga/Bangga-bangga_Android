package com.example.bangga_bangga

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ICanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_i_can)

        var iCanCharacter = findViewById<ImageView>(R.id.i_can_character_img)
        var touchCount = 0

        iCanCharacter.setOnClickListener {
            touchCount += 1
        }

        iCanCharacter.setOnTouchListener { v, event ->
            handleTouch(event)
        }
    }

    private fun handleTouch(event: MotionEvent): Boolean {
        var iCanText = findViewById<TextView>(R.id.i_can_text)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                iCanText.visibility = View.VISIBLE
            }
            MotionEvent.ACTION_UP -> {
                iCanText.visibility = View.INVISIBLE
            }
        }
        return true
    }
}