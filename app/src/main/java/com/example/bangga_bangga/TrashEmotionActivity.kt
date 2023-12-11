package com.example.bangga_bangga

import android.os.Bundle
import android.view.Gravity
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
                        random.nextInt(trashEmotionLayout.width - textView.width),
                        random.nextInt(trashEmotionLayout.height - textView.height),
                        0,
                        0
                    )

                    textView.layoutParams = layoutParams.apply {
                        startToStart = ConstraintSet.PARENT_ID
                        topToTop = ConstraintSet.PARENT_ID
                    }

                    textView.setBackgroundResource(R.drawable.trash)

                    trashEmotionLayout.addView(textView)
                }
            }
        })
    }

}