package com.example.bangga_bangga.model

data class UserInfoResponse(
    val email: String,
    val category: String,
    val nickname: String,
    val age: Int,
    val myPost: MyPostModel,
    val totalPageCount: Int
)