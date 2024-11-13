package com.example.aplicativo_de_cadastros.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicativo_de_cadastros.R
import com.example.aplicativo_de_cadastros.model.Post
import com.example.aplicativo_de_cadastros.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListagemActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem)

        recyclerView = findViewById(R.id.recyclerViewPosts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        postAdapter = PostAdapter(listOf(), this)
        recyclerView.adapter = postAdapter

        RetrofitInstance.api.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        postAdapter.updatePosts(posts)
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(this@ListagemActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
