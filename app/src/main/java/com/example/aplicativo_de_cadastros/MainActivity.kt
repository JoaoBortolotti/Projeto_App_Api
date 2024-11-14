package com.example.aplicativo_de_cadastros

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicativo_de_cadastros.adapters.ItemAdapter
import com.example.aplicativo_de_cadastros.database.DatabaseHelper
import com.example.aplicativo_de_cadastros.models.Item
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var itemList: MutableList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)
        itemList = databaseHelper.getItems().toMutableList()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ItemAdapter(itemList) { item -> onItemSelected(item) }
        recyclerView.adapter = adapter

        val fabAddItem: FloatingActionButton = findViewById(R.id.fab_add_item)
        fabAddItem.setOnClickListener {
            startActivity(Intent(this, AddItemActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        refreshItemList()
    }

    private fun refreshItemList() {
        itemList.clear()
        itemList.addAll(databaseHelper.getItems())
        adapter.notifyDataSetChanged()
    }

    private fun onItemSelected(item: Item) {
        val intent = Intent(this, ViewItemActivity::class.java)
        intent.putExtra("ITEM_ID", item.id)
        startActivity(intent)
    }
}
