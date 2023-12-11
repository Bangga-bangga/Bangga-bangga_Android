package com.example.bangga_bangga

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ICanActivity : AppCompatActivity() {
    lateinit var iCanCharacter: ImageView
    lateinit var iCanText: TextView
    var touchCount = 0
    var originalBottomMargin = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_i_can)

        iCanCharacter = findViewById<ImageView>(R.id.i_can_character_img)
        iCanText = findViewById<TextView>(R.id.i_can_text)

        val layoutParams = iCanCharacter.layoutParams as ViewGroup.MarginLayoutParams
        originalBottomMargin = layoutParams.bottomMargin

        iCanCharacter.setOnTouchListener { v, event ->
            handleTouch(event)
        }
    }

    private fun handleTouch(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                iCanText.visibility = View.VISIBLE

                touchCount += 1

                val scale = 1f + 0.1f * touchCount
                iCanCharacter.scaleX = scale
                iCanCharacter.scaleY = scale

                val layoutParams = iCanCharacter.layoutParams as ViewGroup.MarginLayoutParams
                val newBottomMargin = (originalBottomMargin * scale - 100).toInt()

                updateTextPosition(newBottomMargin)

                updateCharacterBottomMargin(layoutParams, scale)
            }
            MotionEvent.ACTION_UP -> {
                iCanText.visibility = View.INVISIBLE
            }
        }
        return true
    }

    private fun updateCharacterBottomMargin(layoutParams: ViewGroup.MarginLayoutParams, scale: Float) {
        layoutParams.bottomMargin = (originalBottomMargin * scale).toInt()
        iCanCharacter.layoutParams = layoutParams
    }

    private fun updateTextPosition(newBottomMargin: Int) {
        val textLayoutParams = iCanText.layoutParams as ViewGroup.MarginLayoutParams
        textLayoutParams.bottomMargin = newBottomMargin
        iCanText.layoutParams = textLayoutParams
    }
}
