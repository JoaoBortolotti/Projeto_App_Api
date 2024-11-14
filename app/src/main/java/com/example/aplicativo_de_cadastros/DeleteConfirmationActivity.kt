package com.example.aplicativo_de_cadastros

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_de_cadastros.database.DatabaseHelper

class DeleteConfirmationActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private var itemId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_confirmation)

        databaseHelper = DatabaseHelper(this)
        itemId = intent.getIntExtra("ITEM_ID", -1)

        val textViewConfirmMessage: TextView = findViewById(R.id.text_view_confirm_message)
        val buttonYes: Button = findViewById(R.id.button_yes)
        val buttonNo: Button = findViewById(R.id.button_no)

        // Configura mensagem de confirmação
        textViewConfirmMessage.text = "Tem certeza de que deseja excluir este item?"

        buttonYes.setOnClickListener {
            if (itemId != -1) {
                val result = databaseHelper.deleteItem(itemId)
                if (result > 0) {
                    Toast.makeText(this, "Item excluído com sucesso", Toast.LENGTH_SHORT).show()
                    finish() // Volta para a tela anterior
                } else {
                    Toast.makeText(this, "Erro ao excluir item", Toast.LENGTH_SHORT).show()
                }
            }
        }

        buttonNo.setOnClickListener {
            finish() // Fecha a tela de confirmação sem excluir
        }
    }
}
