package com.example.aplicativo_de_cadastros.network

import com.example.aplicativo_de_cadastros.models.Item
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("items")
    fun getItems(): Call<List<Item>>

    @GET("items/{id}")
    fun getItemById(@Path("id") id: String): Call<Item>

    @POST("items")
    fun createItem(@Body newItem: Item): Call<Item>

    @PUT("items/{id}")
    fun updateItem(@Path("id") id: String, @Body itemUpdate: Item): Call<Item>

    @DELETE("items/{id}")
    fun deleteItem(@Path("id") id: String): Call<Unit>
}

