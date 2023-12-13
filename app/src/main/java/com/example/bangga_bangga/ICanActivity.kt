package com.example.bangga_bangga

import android.content.Intent
import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class ICanActivity : AppCompatActivity() {
    private lateinit var iCanCharacter: ImageView
    private lateinit var iCanText: TextView
    private lateinit var iCanLayout: ConstraintLayout
    private lateinit var popUpLayout: LinearLayout
    private lateinit var convertHomeBtn: Button
    private var touchCount = 0
    private var originalBottomMargin = 0

    private var mediaPlayer: MediaPlayer? = null
    private var effectPlayer: MediaPlayer? = null
    private var popEffectPlayer: MediaPlayer? = null

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
        popEffectPlayer = MediaPlayer.create(this, R.raw.pop_up_effect)

        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        iCanCharacter = findViewById(R.id.i_can_character_img)
        iCanText = findViewById(R.id.i_can_text)
        iCanLayout = findViewById(R.id.i_can_layout)
        popUpLayout = findViewById(R.id.pop_up_layout)
        convertHomeBtn = findViewById(R.id.convert_home_btn)

        val layoutParams = iCanCharacter.layoutParams as ViewGroup.MarginLayoutParams
        originalBottomMargin = layoutParams.bottomMargin

        iCanCharacter.setOnTouchListener { _, event ->
            handleTouch(event)
        }

        fun convertToHome() {
            mediaPlayer?.release()
            mediaPlayer = null

            effectPlayer?.release()
            effectPlayer = null

            popEffectPlayer?.release()
            popEffectPlayer = null

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        convertHomeBtn.setOnClickListener {
            convertToHome()
        }

    }

    private fun playPopEffect() {
        popEffectPlayer?.start()
    }

    private fun handleTouch(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                iCanCharacter.setImageResource(R.drawable.i_can_character_a)
                iCanText.visibility = View.VISIBLE

                val scale = 1f + 0.08f * touchCount
                iCanCharacter.scaleX = scale
                iCanCharacter.scaleY = scale

                val layoutParams = iCanCharacter.layoutParams as ViewGroup.MarginLayoutParams
                val newBottomMargin = (originalBottomMargin * scale - 100).toInt()

                updateTextPosition(newBottomMargin)

                updateCharacterBottomMargin(layoutParams, scale)
            }
            MotionEvent.ACTION_UP -> {
                touchCount += 1
                playSound()
                iCanText.visibility = View.INVISIBLE
                iCanCharacter.setImageResource(R.drawable.i_can_character_b)

                if (touchCount == 50) {
                    playPopEffect()
                    popUpLayout.visibility = View.VISIBLE
                    iCanLayout.visibility = View.GONE
                }

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
        popEffectPlayer?.release()
        popEffectPlayer = null
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
