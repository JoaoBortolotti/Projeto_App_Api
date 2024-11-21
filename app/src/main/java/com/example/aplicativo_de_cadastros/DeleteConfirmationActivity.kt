package com.example.aplicativo_de_cadastros

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_de_cadastros.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteConfirmationActivity : AppCompatActivity() {

    private var itemId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_confirmation)

        itemId = intent.getIntExtra("ITEM_ID", -1)

        val textViewConfirmMessage: TextView = findViewById(R.id.text_view_confirm_message)
        val buttonYes: Button = findViewById(R.id.button_yes)
        val buttonNo: Button = findViewById(R.id.button_no)

        textViewConfirmMessage.text = "Tem certeza de que deseja excluir este item?"

        buttonYes.setOnClickListener {
            deleteItem(itemId)
        }

        buttonNo.setOnClickListener {
            finish()
        }
    }

    private fun deleteItem(id: Int) {
        RetrofitClient.instance.deleteItem(id.toString()).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@DeleteConfirmationActivity, "Item excluído com sucesso!", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish() // Retorna à MainActivity
                } else {
                    Toast.makeText(this@DeleteConfirmationActivity, "Erro ao excluir item", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(this@DeleteConfirmationActivity, "Falha na requisição: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
