package com.example.bangga_bangga.api

import android.content.Context
import com.example.bangga_bangga.model.LikeModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.PUT
import retrofit2.http.Path

interface LikeApi {
    @PUT("/posts/{id}/like")
    suspend fun like(
        @Path("id") id: Int
    ): Response<LikeModel>

    companion object {
        private const val BASE_URL = "http://ec2-13-125-135-255.ap-northeast-2.compute.amazonaws.com:8080/"
        val gson: Gson = GsonBuilder().setLenient().create()

        fun create(context: Context): LikeApi {
            val preToken = context.getSharedPreferences("userToken", Context.MODE_PRIVATE)
            val token = preToken.getString("token", null)

            val client = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "$token")
                        .build()
                    chain.proceed(newRequest)
                })
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(LikeApi::class.java)
        }
    }
}