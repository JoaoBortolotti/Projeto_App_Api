package com.example.aplicativo_de_cadastros

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicativo_de_cadastros.database.DatabaseHelper
import com.example.aplicativo_de_cadastros.models.Item

class ViewItemActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
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

        databaseHelper = DatabaseHelper(this)
        textViewName = findViewById(R.id.text_view_name)
        textViewDescription = findViewById(R.id.text_view_description)
        buttonEdit = findViewById(R.id.button_edit)
        buttonDelete = findViewById(R.id.button_delete)

        itemId = intent.getIntExtra("ITEM_ID", -1)
        if (itemId != -1) {
            loadItemDetails(itemId)
        }

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

    override fun onResume() {
        super.onResume()
        loadItemDetails(itemId) // Recarrega os detalhes ao voltar para esta tela
    }

    private fun loadItemDetails(id: Int) {
        val item: Item? = databaseHelper.getItems().find { it.id == id }
        if (item != null) {
            textViewName.text = item.name
            textViewDescription.text = item.description
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_DELETE && resultCode == RESULT_OK) {
            finish() // Fecha a ViewItemActivity e retorna à MainActivity
        }
    }
}
