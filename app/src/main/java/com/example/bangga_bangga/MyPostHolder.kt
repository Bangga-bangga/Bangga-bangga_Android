package com.example.bangga_bangga

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bangga_bangga.model.PreviewModel

class MyPostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val myTitleTextView: TextView = itemView.findViewById(R.id.myTitle)
    private val myLikeCountView: TextView = itemView.findViewById(R.id.myLikeCount)
    private val myCommentCountView: TextView = itemView.findViewById(R.id.myCommentCount)
    private val myPostId: TextView = itemView.findViewById(R.id.myPostId)

    private val context: Context = itemView.context

    fun bind(post: PreviewModel){
        myPostId.text = post.id.toString()
        myTitleTextView.text = post.title
        myLikeCountView.text = post.likeCount.toString()
        myCommentCountView.text = post.commentCount.toString()
        itemView.setOnClickListener{
            // 상세 보기 액티비티로 바꾸기
            val intent = Intent(context, ViewPostActivity::class.java).apply {
                putExtra("postId", post.id)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            Log.d("클릭된 게시글 id",intent.extras.toString())
            context.startActivity(intent)

        }
    }
}