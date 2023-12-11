package com.example.bangga_bangga

import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import java.util.Random

class TrashEmotionActivity : AppCompatActivity() {
    private var keywordList: MutableList<String>? = null
    private lateinit var trashEmotionLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transh_emotion)

        trashEmotionLayout = findViewById(R.id.trash_emotion_layout)
        keywordList = intent.getStringArrayListExtra("keywordList")?.toMutableList()

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

                    textView.setBackgroundResource(R.drawable.trash)

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
        }
        return true
    }
}