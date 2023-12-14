package com.example.bangga_bangga.api

import android.content.Context
import android.util.Log
import com.example.bangga_bangga.model.UserInfoResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UserInfoApi {
    @GET("/user/me")
    fun getUserInfo(
        @Query("size") size: Int,
        @Query("page") page: Int
    ): Call<UserInfoResponse>

    companion object {
        private const val BASE_URL = "http://ec2-13-125-135-255.ap-northeast-2.compute.amazonaws.com:8080/"
        val gson: Gson = GsonBuilder().setLenient().create();

        fun createUserInfo(context: Context): UserInfoApi {
            val prefToken =  context.getSharedPreferences("userToken", Context.MODE_PRIVATE)
            val token = prefToken.getString("token", null)

            //헤더에 Authorization 토큰 넣기
            val client = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "$token")
                        .build()
                    Log.d("요청", newRequest.toString())
                    chain.proceed(newRequest)
                })
                .build()

            //헤더와 함께 요청
            return Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson)).build()
                .create(UserInfoApi::class.java)
        }
    }
}