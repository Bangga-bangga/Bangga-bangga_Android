package com.example.bangga_bangga.model

data class ViewPostModel(
    val id: Long,
    val nickname: String,
    val title: String,
    val content: String,
    val comments: List<Comment>,
    val likeCount: Long,
    val createdAt: String,
    val liked: Boolean,
)

data class Comment(
    val id: Long,
    val nickname: String,
    val content: String,
    val createdAt: String,
)