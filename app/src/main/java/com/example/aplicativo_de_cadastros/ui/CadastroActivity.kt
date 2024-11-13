package com.example.aplicativo_de_cadastros.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_de_cadastros.R
import com.example.aplicativo_de_cadastros.model.Post
import com.example.aplicativo_de_cadastros.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val titleEditText = findViewById<EditText>(R.id.editTextTitle)
        val bodyEditText = findViewById<EditText>(R.id.editTextBody)
        val submitButton = findViewById<Button>(R.id.buttonSubmit)

        submitButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val body = bodyEditText.text.toString()

            if (title.isNotEmpty() && body.isNotEmpty()) {
                val newPost = Post(title = title, body = body)
                RetrofitInstance.api.createPost(newPost).enqueue(object : Callback<Post> {
                    override fun onResponse(call: Call<Post>, response: Response<Post>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@CadastroActivity, "Post criado com sucesso!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<Post>, t: Throwable) {
                        Toast.makeText(this@CadastroActivity, "Erro ao criar post: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
