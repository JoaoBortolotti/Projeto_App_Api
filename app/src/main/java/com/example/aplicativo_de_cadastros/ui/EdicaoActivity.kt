package com.example.aplicativo_de_cadastros.ui

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_de_cadastros.R
import com.example.aplicativo_de_cadastros.model.Post
import com.example.aplicativo_de_cadastros.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EdicaoActivity : AppCompatActivity() {
    private lateinit var post: Post

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edicao)

        val titleEditText = findViewById<EditText>(R.id.editTextTitleEdit)
        val bodyEditText = findViewById<EditText>(R.id.editTextBodyEdit)
        val updateButton = findViewById<Button>(R.id.buttonUpdate)

        post = intent.getParcelableExtra("post", Post::class.java)!!

        titleEditText.setText(post.title)
        bodyEditText.setText(post.body)

        updateButton.setOnClickListener {
            val updatedTitle = titleEditText.text.toString()
            val updatedBody = bodyEditText.text.toString()

            val updatedPost = post.copy(title = updatedTitle, body = updatedBody)
            RetrofitInstance.api.updatePost(post.id, updatedPost).enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@EdicaoActivity, "Post atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    Toast.makeText(this@EdicaoActivity, "Erro ao atualizar: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
