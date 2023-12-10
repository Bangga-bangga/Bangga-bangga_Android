package com.example.bangga_bangga

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bangga_bangga.model.MyPostModel
import com.example.bangga_bangga.model.PreviewModel
class MyPostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val myTitleTextView: TextView = itemView.findViewById(R.id.myTitle)
    private val myContentTextView: TextView = itemView.findViewById(R.id.myContent)
    private val myLikeCountView: TextView = itemView.findViewById(R.id.myLikeCount)
    private val myCommentCountView: TextView = itemView.findViewById(R.id.myCommentCount)


    fun bind(post: MyPostModel){
        myTitleTextView.text = post.title
        myContentTextView.text = post.content
        myLikeCountView.text = post.like.toString()
        myCommentCountView.text = post.comment.toString()
    }
}