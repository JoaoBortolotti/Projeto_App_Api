package com.example.aplicativo_de_cadastros

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_de_cadastros.models.Item
import com.example.aplicativo_de_cadastros.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddItemActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        editTextName = findViewById(R.id.edit_text_name)
        editTextDescription = findViewById(R.id.edit_text_description)
        buttonSave = findViewById(R.id.button_save)

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val description = editTextDescription.text.toString()

            if (name.isNotEmpty() && description.isNotEmpty()) {
                val newItem = Item(id = 0, name = name, description = description)
                saveItem(newItem)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume(){

    }

    private fun saveItem(item: Item) {
        RetrofitClient.instance.createItem(item).enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddItemActivity, "Item adicionado com sucesso!", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@AddItemActivity, "Erro ao adicionar item", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                Toast.makeText(this@AddItemActivity, "Falha na requisição: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
