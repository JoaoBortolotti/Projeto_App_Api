package com.example.aplicativo_de_cadastros.network

import com.example.aplicativo_de_cadastros.models.Item
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // Exemplo de uma requisição GET para listar todos os itens
    @GET("items")
    fun getItems(): Call<List<Item>>

    // Exemplo de uma requisição POST para adicionar um novo item
    @POST("items")
    fun addItem(@Body item: Item): Call<Item>

    // Exemplo de uma requisição GET para buscar um item específico pelo ID
    @GET("items/{id}")
    fun getItemById(@Path("id") id: Int): Call<Item>
}
