package com.example.bangga_bangga.model
data class UserInfoModel(
    var email : String ?= null,
    var category : String ?= null,
    var nickname : String ?= null,
    var age : Int ?= null,
    var posts : List<PreviewModel> ?= null
)