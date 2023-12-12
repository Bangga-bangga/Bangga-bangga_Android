package com.example.bangga_bangga

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bangga_bangga.model.MyPostModel
import com.example.bangga_bangga.model.PreviewModel
import com.example.bangga_bangga.model.UserInfoModel

class MyPostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val myTitleTextView: TextView = itemView.findViewById(R.id.myTitle)
    private val myLikeCountView: TextView = itemView.findViewById(R.id.myLikeCount)
    private val myCommentCountView: TextView = itemView.findViewById(R.id.myCommentCount)


    fun bind(post: PreviewModel){
        myTitleTextView.text = post.title
        myLikeCountView.text = post.likeCount.toString()
        myCommentCountView.text = post.commentCount.toString()
    }
}