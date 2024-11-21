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

class EditItemActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var buttonUpdate: Button
    private var itemId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)

        itemId = intent.getIntExtra("ITEM_ID", -1)

        editTextName = findViewById(R.id.edit_text_name)
        editTextDescription = findViewById(R.id.edit_text_description)
        buttonUpdate = findViewById(R.id.button_update)

        buttonUpdate.setOnClickListener {
            val name = editTextName.text.toString()
            val description = editTextDescription.text.toString()

            if (name.isNotEmpty() && description.isNotEmpty()) {
                val updatedItem = Item(id = itemId, name = name, description = description)
                updateItem(updatedItem)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateItem(item: Item) {
        RetrofitClient.instance.updateItem(item.id.toString(), item).enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditItemActivity, "Item atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditItemActivity, "Erro ao atualizar item", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                Toast.makeText(this@EditItemActivity, "Falha na requisição: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
