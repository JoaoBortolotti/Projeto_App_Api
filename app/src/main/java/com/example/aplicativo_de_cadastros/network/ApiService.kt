package com.example.aplicativo_de_cadastros.network

import com.example.aplicativo_de_cadastros.model.Post
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @POST("posts")
    fun createPost(@Body post: Post): Call<Post>

    @PUT("posts/{id}")
    fun updatePost(@Path("id") id: Int, @Body post: Post): Call<Post>
}
