package com.example.bangga_bangga

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bangga_bangga.model.PreviewModel

class PreviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.previewTitle)
    private val likeCountView: TextView = itemView.findViewById(R.id.likeCount)
    private val commentCountView: TextView = itemView.findViewById(R.id.commentCount)
    private val nicknameView: TextView = itemView.findViewById(R.id.nickname)

    fun bind(post: PreviewModel){
        titleTextView.text = post.title
        likeCountView.text = post.likeCount.toString()
        commentCountView.text = post.commentCount.toString()
        nicknameView.text = post.writer
    }
}