package com.example.bangga_bangga.api

import com.example.bangga_bangga.model.LoginBackendResponse
import com.example.bangga_bangga.model.UserModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    //@Headers("app/json")
    @POST("/login")
    fun userLogin(
        @Body jsonParams: UserModel,
    ): Call<LoginBackendResponse>


    companion object {
        private const val BASE_URL = "http:ec2-13-125-135-255.ap-northeast-2.compute.amazonaws.com:8080/"
        val gson: Gson = GsonBuilder().setLenient().create();

        fun create(): Api {

            return Retrofit.Builder().baseUrl(BASE_URL)
                //.client(client)
                .addConverterFactory(GsonConverterFactory.create(gson)).build()
                .create(Api::class.java)
        }
    }
}