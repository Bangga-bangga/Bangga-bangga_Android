package com.example.bangga_bangga

import android.graphics.Rect
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import java.util.Random

class TrashEmotionActivity : AppCompatActivity() {
    private var keywordList: MutableList<String>? = null
    private lateinit var trashEmotionLayout: ConstraintLayout
    private lateinit var trashCan: ImageView
    private var mediaPlayer: MediaPlayer? = null
    private var effectPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transh_emotion)

        mediaPlayer = MediaPlayer.create(this, R.raw.trash_emotion_bgm)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        effectPlayer = MediaPlayer.create(this, R.raw.trash_effect)

        trashEmotionLayout = findViewById(R.id.trash_emotion_layout)
        keywordList = intent.getStringArrayListExtra("keywordList")?.toMutableList()

        trashCan = findViewById<ImageView>(R.id.trash_can_img)

        /** 툴바 생성 **/
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        supportActionBar?.title = ""
        /** 툴바 코드 끝 아래 onOptionsItemSelected또한 복사**/

        displayKeywordsRandomly()
    }

    private fun displayKeywordsRandomly() {
        val trashEmotionLayout = findViewById<ConstraintLayout>(R.id.trash_emotion_layout)

        trashEmotionLayout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                trashEmotionLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val random = Random()
                for (keyword in keywordList.orEmpty()) {
                    val textView = TextView(this@TrashEmotionActivity)
                    textView.text = keyword
                    textView.id = View.generateViewId()
                    textView.gravity = Gravity.CENTER

                    val layoutParams = ConstraintLayout.LayoutParams(
                        300,
                        300
                    )

                    layoutParams.setMargins(
                        random.nextInt(trashEmotionLayout.width - textView.width - 600),
                        random.nextInt(trashEmotionLayout.height - textView.height - 600),
                        0,
                        0
                    )

                    textView.layoutParams = layoutParams.apply {
                        startToStart = ConstraintSet.PARENT_ID
                        topToTop = ConstraintSet.PARENT_ID
                    }

                    textView.setBackgroundResource(R.drawable.trash_img)

                    textView.setOnTouchListener { view, event ->
                        handleTouch(view, event)
                    }

                    trashEmotionLayout.addView(textView)
                }
            }
        })
    }

    private fun handleTouch(view: View, event: MotionEvent): Boolean {
        val rawX = event.rawX.toInt()
        val rawY = event.rawY.toInt()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val layoutParams = view.layoutParams as ConstraintLayout.LayoutParams
                val xDelta = rawX - layoutParams.leftMargin
                val yDelta = rawY - layoutParams.topMargin

                view.setTag(R.id.tag_x, xDelta)
                view.setTag(R.id.tag_y, yDelta)
            }
            MotionEvent.ACTION_MOVE -> {
                val xDelta = view.getTag(R.id.tag_x) as Int
                val yDelta = view.getTag(R.id.tag_y) as Int

                val newLeftMargin = rawX - xDelta
                val newTopMargin = rawY - yDelta

                val layoutParams = view.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.leftMargin = newLeftMargin
                layoutParams.topMargin = newTopMargin
                view.layoutParams = layoutParams
            }
            MotionEvent.ACTION_UP -> {
                if (isViewOverlapping(view, trashCan)) {
                    trashEmotionLayout.removeView(view)
                    playEffect()
                }
            }
        }
        return true
    }

    private fun playEffect() {
        effectPlayer?.start()
    }

    private fun isViewOverlapping(view1: View, view2: View): Boolean {
        val rect1 = Rect()
        view1.getHitRect(rect1)

        val rect2 = Rect()
        view2.getHitRect(rect2)

        return Rect.intersects(rect1, rect2)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null

        effectPlayer?.release()
        effectPlayer = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}