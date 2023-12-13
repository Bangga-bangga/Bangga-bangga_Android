package com.example.bangga_bangga

import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ICanActivity : AppCompatActivity() {
    private lateinit var iCanCharacter: ImageView
    private lateinit var iCanText: TextView
    private var touchCount = 0
    private var originalBottomMargin = 0

    private var mediaPlayer: MediaPlayer? = null
    private var effectPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_i_can)

        /** 툴바 생성 **/
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        supportActionBar?.title = ""
        /** 툴바 코드 끝 아래 onOptionsItemSelected또한 복사**/

        mediaPlayer = MediaPlayer.create(this, R.raw.i_can_bgm)
        effectPlayer = MediaPlayer.create(this, R.raw.i_can_effect)

        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        iCanCharacter = findViewById(R.id.i_can_character_img)
        iCanText = findViewById(R.id.i_can_text)

        val layoutParams = iCanCharacter.layoutParams as ViewGroup.MarginLayoutParams
        originalBottomMargin = layoutParams.bottomMargin

        iCanCharacter.setOnTouchListener { _, event ->
            handleTouch(event)
        }

    }

    private fun handleTouch(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                iCanCharacter.setImageResource(R.drawable.i_can_character_a)
                iCanText.visibility = View.VISIBLE


                touchCount += 1

                val scale = 1f + 0.08f * touchCount
                iCanCharacter.scaleX = scale
                iCanCharacter.scaleY = scale

                val layoutParams = iCanCharacter.layoutParams as ViewGroup.MarginLayoutParams
                val newBottomMargin = (originalBottomMargin * scale - 100).toInt()

                updateTextPosition(newBottomMargin)

                updateCharacterBottomMargin(layoutParams, scale)
            }
            MotionEvent.ACTION_UP -> {
                playSound()
                iCanText.visibility = View.INVISIBLE
                iCanCharacter.setImageResource(R.drawable.i_can_character_b)

            }
        }
        return true
    }

    private fun playSound() {
        effectPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        effectPlayer?.release()
        effectPlayer = null
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
