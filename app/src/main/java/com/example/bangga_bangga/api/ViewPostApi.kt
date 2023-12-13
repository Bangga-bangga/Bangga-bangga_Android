package com.example.bangga_bangga.api

import android.content.Context
import com.example.bangga_bangga.model.ViewPostModel
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ViewPostApi {
    @GET("/posts/{id}")
    fun viewPost(
        @Path("id") id: Int,
    ): Call<ViewPostModel>

    companion object {
        private const val BASE_URL = "http:ec2-13-125-135-255.ap-northeast-2.compute.amazonaws.com:8080/"
        val gson: Gson = Gson()

        fun create(context: Context): ViewPostApi {
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
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ViewPostApi::class.java)
        }
    }


}