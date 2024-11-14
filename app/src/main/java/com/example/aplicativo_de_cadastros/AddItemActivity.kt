package com.example.aplicativo_de_cadastros

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_de_cadastros.database.DatabaseHelper

class AddItemActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var editTextName: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        databaseHelper = DatabaseHelper(this)
        editTextName = findViewById(R.id.edit_text_name)
        editTextDescription = findViewById(R.id.edit_text_description)
        buttonSave = findViewById(R.id.button_save)

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val description = editTextDescription.text.toString()

            if (name.isNotEmpty() && description.isNotEmpty()) {
                val id = databaseHelper.addItem(name, description)
                if (id != -1L) {
                    Toast.makeText(this, "Item adicionado com sucesso", Toast.LENGTH_SHORT).show()
                    finish() // Volta para a tela anterior
                } else {
                    Toast.makeText(this, "Erro ao adicionar item", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
