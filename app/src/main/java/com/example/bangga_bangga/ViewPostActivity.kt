package com.example.bangga_bangga

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.bangga_bangga.api.CommentApi
import com.example.bangga_bangga.api.CommentRequest
import com.example.bangga_bangga.api.ViewPostApi
import com.example.bangga_bangga.api.LikeApi
import com.example.bangga_bangga.model.CommentModel
import com.example.bangga_bangga.model.ViewPostModel
import com.example.bangga_bangga.model.LikeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ViewPostActivity : AppCompatActivity() {
    private var postId: Int = -1

    private lateinit var nickname: TextView
    private lateinit var title: TextView
    private lateinit var content: TextView
    private lateinit var likeCount: TextView
    private lateinit var commentCount: TextView
    private lateinit var createAt: TextView
    private lateinit var commentContainer: LinearLayout
    private lateinit var commentField: EditText
    private lateinit var commentSendBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val bundle: Bundle? = intent.extras

        if (bundle != null && bundle.containsKey("postId")) {
            postId = bundle.getInt("postId")

            Log.d("PostId", "$postId")
            fetchDataFromServer(postId)

        } else {
            Log.d("PostId", "fail")
        }

        val likeBtn = findViewById<ImageView>(R.id.like_btn)

        likeBtn.setOnClickListener {
            putLike(postId)
        }

        commentSendBtn = findViewById(R.id.comment_send_btn)

        commentSendBtn.setOnClickListener {
            commentField = findViewById(R.id.comment_field)
            if (commentField.text.isNotEmpty()) {
                writeComment(postId, CommentRequest(commentField.text.toString()))
                commentField.text.clear()

                recreate()
            }
        }
    }

    private fun fetchDataFromServer(postId: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val viewPostService = ViewPostApi.create(this@ViewPostActivity)
                val response: Response<ViewPostModel> = viewPostService.viewPost(postId)

                if (response.isSuccessful) {
                    val postResponse = response.body()
                    Log.d("ViewPostActivity", postResponse.toString())

                    updateUI(postResponse)

                } else {
                    Log.d("ViewPostActivity", response.code().toString())
                }
            } catch (e: IOException) {
                Log.e("ViewPostActivity", "Error: ${e.message}")
            }
        }
    }

    private fun putLike(postId: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val likeService = LikeApi.create(this@ViewPostActivity)
                val response: Response<LikeModel> = likeService.like(postId)

                if (response.isSuccessful) {
                    val postResponse = response.body()
                    Log.d("ViewPostActivity", postResponse.toString())

                    fetchDataFromServer(postId)
                } else {
                    Log.d("ViewPostActivity", response.code().toString())
                }
            } catch (e: IOException) {
                Log.e("ViewPostActivity", "Error: ${e.message}")
            }
        }
    }

    private fun writeComment(postId: Int, content: CommentRequest) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val commentService = CommentApi.create(this@ViewPostActivity)
                val response: Response<CommentModel> = commentService.comment(postId, content)

                if (response.isSuccessful) {
                    val postResponse = response.body()
                    Log.d("ViewPostActivity", postResponse.toString())

                } else {
                    Log.d("ViewPostActivity", response.code().toString())
                }
            } catch (e: IOException) {
                Log.e("ViewPostActivity", "Error: ${e.message}")
            }
        }
    }

    private fun updateUI(postResponse: ViewPostModel?) {
        nickname = findViewById(R.id.nickname)
        title = findViewById(R.id.title)
        content = findViewById(R.id.content)
        likeCount = findViewById(R.id.likeCount)
        commentCount = findViewById(R.id.commentCount)
        createAt = findViewById(R.id.createAt)
        commentContainer = findViewById(R.id.comment_container)

        val inflater = LayoutInflater.from(this)

        postResponse?.let {
            nickname.text = it.nickname
            title.text = it.title
            content.text = it.content
            likeCount.text = "${it.likeCount}개의 좋아요"
            commentCount.text = "${it.comments.size}개의 댓글"
            createAt.text = it.createdAt

            commentContainer.removeAllViews()

            for (comment in it.comments) {

                val commentView = inflater.inflate(R.layout.item_comment, null)

                val commentNickname = commentView.findViewById<TextView>(R.id.comment_nickname)
                val commentCreateAt = commentView.findViewById<TextView>(R.id.comment_createAt)
                val commentContent = commentView.findViewById<TextView>(R.id.comment_content)

                commentNickname.text = comment.nickname
                commentCreateAt.text = comment.createdAt
                commentContent.text = comment.content.toString()

                commentContainer.addView(commentView)

            }
        }
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
