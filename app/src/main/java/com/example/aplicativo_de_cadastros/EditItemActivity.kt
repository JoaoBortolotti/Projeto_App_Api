package com.example.aplicativo_de_cadastros

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_de_cadastros.database.DatabaseHelper
import com.example.aplicativo_de_cadastros.models.Item

class EditItemActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var editTextName: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var buttonUpdate: Button
    private var itemId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)

        databaseHelper = DatabaseHelper(this)
        editTextName = findViewById(R.id.edit_text_name)
        editTextDescription = findViewById(R.id.edit_text_description)
        buttonUpdate = findViewById(R.id.button_update)

        itemId = intent.getIntExtra("ITEM_ID", -1)
        if (itemId != -1) {
            loadItemDetails(itemId)
        }

        buttonUpdate.setOnClickListener {
            val name = editTextName.text.toString()
            val description = editTextDescription.text.toString()

            if (name.isNotEmpty() && description.isNotEmpty()) {
                val result = databaseHelper.updateItem(itemId, name, description)
                if (result > 0) {
                    Toast.makeText(this, "Item atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    finish() // Volta para a tela anterior
                } else {
                    Toast.makeText(this, "Erro ao atualizar item", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadItemDetails(id: Int) {
        val item: Item? = databaseHelper.getItems().find { it.id == id }
        if (item != null) {
            editTextName.setText(item.name)
            editTextDescription.setText(item.description)
        }
    }
}
