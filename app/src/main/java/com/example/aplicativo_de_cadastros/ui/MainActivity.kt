package com.example.aplicativo_de_cadastros.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_de_cadastros.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonCadastro).setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }

        findViewById<Button>(R.id.buttonListagem).setOnClickListener {
            startActivity(Intent(this, ListagemActivity::class.java))
        }

        findViewById<Button>(R.id.buttonEditar).setOnClickListener {
            startActivity(Intent(this, EdicaoActivity::class.java))
        }
    }
}
