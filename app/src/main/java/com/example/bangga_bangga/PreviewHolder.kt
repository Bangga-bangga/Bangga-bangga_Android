package com.example.bangga_bangga

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bangga_bangga.model.PreviewModel

class PreviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.previewTitle)
    private val likeCountView: TextView = itemView.findViewById(R.id.likeCount)
    private val commentCountView: TextView = itemView.findViewById(R.id.commentCount)
    private val nicknameView: TextView = itemView.findViewById(R.id.nickname)
    private val postId: TextView = itemView.findViewById(R.id.postId)

    private val context: Context = itemView.context

    fun bind(post: PreviewModel){
        postId.text = post.id.toString()
        titleTextView.text = post.title
        likeCountView.text = post.likeCount.toString()
        commentCountView.text = post.commentCount.toString()
        nicknameView.text = post.writer
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
