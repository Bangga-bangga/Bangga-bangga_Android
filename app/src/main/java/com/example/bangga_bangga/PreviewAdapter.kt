package com.example.bangga_bangga

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bangga_bangga.model.PreviewModel

class PreviewAdapter(private val posts: MutableList<PreviewModel> = mutableListOf()) :
    RecyclerView.Adapter<PreviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.preview_post, parent, false)
        return PreviewHolder(view)
    }

    override fun onBindViewHolder(holder: PreviewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = posts.size

    fun setPosts(newPosts: List<PreviewModel>) {
        posts.clear()
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }
}