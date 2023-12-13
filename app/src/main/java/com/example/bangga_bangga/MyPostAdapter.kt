package com.example.bangga_bangga

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bangga_bangga.model.PreviewModel

class MyPostAdapter(private val posts: MutableList<PreviewModel> = mutableListOf()) :
    RecyclerView.Adapter<MyPostHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_post, parent, false)
        return MyPostHolder(view)
    }

    override fun onBindViewHolder(holder: MyPostHolder, position: Int) {
//        val user = userInfo
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = posts.size

    fun setUserInfo(newPost: List<PreviewModel>) {
        posts.clear()
        posts.addAll(newPost)
//        this.userInfo = user
        notifyDataSetChanged()
    }
}