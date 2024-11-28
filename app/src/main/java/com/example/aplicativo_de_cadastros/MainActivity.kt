package com.example.aplicativo_de_cadastros

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicativo_de_cadastros.adapters.ItemAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.aplicativo_de_cadastros.models.Item
import com.example.aplicativo_de_cadastros.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var itemList: MutableList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemList = mutableListOf()

        adapter = ItemAdapter(itemList) { item -> onItemSelected(item) }
        recyclerView.adapter = adapter

        val fabAddItem: FloatingActionButton = findViewById(R.id.fab_add_item)
        fabAddItem.setOnClickListener {
            startActivityForResult(Intent(this, AddItemActivity::class.java), ADD_ITEM_REQUEST_CODE)
        }

        fetchItems()
    }

    private fun fetchItems() {
        RetrofitClient.instance.getItems().enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    val newItemList = response.body() ?: emptyList()

                    adapter.updateItems(newItemList)
                } else {
                    Toast.makeText(this@MainActivity, "Erro ao carregar itens", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Falha na requisição: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onItemSelected(item: Item) {
        val intent = Intent(this, ViewItemActivity::class.java)
        intent.putExtra("ITEM_ID", item.id)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == ADD_ITEM_REQUEST_CODE || requestCode == DELETE_ITEM_REQUEST_CODE) && resultCode == RESULT_OK) {
            fetchItems()
        }
    }

    companion object {
        const val ADD_ITEM_REQUEST_CODE = 1001
        const val DELETE_ITEM_REQUEST_CODE = 1002
    }
}