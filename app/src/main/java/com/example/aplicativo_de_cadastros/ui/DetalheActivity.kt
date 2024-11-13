package com.example.aplicativo_de_cadastros.ui

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_de_cadastros.R
import com.example.aplicativo_de_cadastros.model.Post

class DetalheActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe)

        val titleTextView = findViewById<TextView>(R.id.textViewTitleDetail)
        val bodyTextView = findViewById<TextView>(R.id.textViewBodyDetail)


        val post = intent.getParcelableExtra("post", Post::class.java)
        post?.let {
            titleTextView.text = it.title
            bodyTextView.text = it.body
        }
    }
}
