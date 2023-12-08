package com.example.bangga_bangga.api

import com.example.bangga_bangga.model.LoginResponse
import com.example.bangga_bangga.model.UserModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    //@Headers("app/json")
    @POST("/login")
    fun userLogin(
        @Body jsonParams: UserModel,
    ): Call<LoginResponse>


    companion object {
        private const val BASE_URL = "http:ec2-13-125-135-255.ap-northeast-2.compute.amazonaws.com:8080/"
        val gson: Gson = GsonBuilder().setLenient().create();

        fun create(): LoginApi {

            return Retrofit.Builder().baseUrl(BASE_URL)
                //.client(client)
                .addConverterFactory(GsonConverterFactory.create(gson)).build()
                .create(LoginApi::class.java)
        }
    }
}