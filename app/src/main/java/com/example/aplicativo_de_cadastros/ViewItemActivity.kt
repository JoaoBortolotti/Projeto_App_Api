package com.example.aplicativo_de_cadastros

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_de_cadastros.models.Item
import com.example.aplicativo_de_cadastros.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewItemActivity : AppCompatActivity() {

    private lateinit var textViewName: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button
    private var itemId: Int = -1

    companion object {
        private const val REQUEST_CODE_DELETE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)

        itemId = intent.getIntExtra("ITEM_ID", -1)

        textViewName = findViewById(R.id.text_view_name)
        textViewDescription = findViewById(R.id.text_view_description)
        buttonEdit = findViewById(R.id.button_edit)
        buttonDelete = findViewById(R.id.button_delete)

        loadItemDetails(itemId)

        buttonEdit.setOnClickListener {
            val intent = Intent(this, EditItemActivity::class.java)
            intent.putExtra("ITEM_ID", itemId)
            startActivity(intent)
        }

        buttonDelete.setOnClickListener {
            val intent = Intent(this, DeleteConfirmationActivity::class.java)
            intent.putExtra("ITEM_ID", itemId)
            startActivityForResult(intent, REQUEST_CODE_DELETE)
        }
    }

    private fun loadItemDetails(id: Int) {
        RetrofitClient.instance.getItemById(id.toString()).enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    val item = response.body()
                    item?.let {
                        textViewName.text = it.name
                        textViewDescription.text = it.description
                    }
                } else {
                    Toast.makeText(this@ViewItemActivity, "Erro ao carregar item", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                Toast.makeText(this@ViewItemActivity, "Falha na requisição: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_DELETE && resultCode == RESULT_OK) {
            finish()
        }
    }
}
