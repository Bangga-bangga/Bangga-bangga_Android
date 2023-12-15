package com.example.bangga_bangga.api

import android.content.Context
import com.example.bangga_bangga.model.CommentModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentApi {
    @POST("/posts/{id}/comments")
    suspend fun comment(
        @Path("id") id: Int,
        @Body content: String
    ): Response<CommentModel>

    companion object {
        private const val BASE_URL = "http://ec2-13-125-135-255.ap-northeast-2.compute.amazonaws.com:8080/"
        val gson: Gson = GsonBuilder().setLenient().create()

        fun create(context: Context): CommentApi {
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
                .create(CommentApi::class.java)
        }
    }


}
