package com.example.bangga_bangga

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bangga_bangga.model.PreviewModel

class PreviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.previewTitle)
    private val contentTextView: TextView = itemView.findViewById(R.id.previewContent)

    fun bind(post: PreviewModel){
        titleTextView.text = post.title
        contentTextView.text = post.content
    }
}