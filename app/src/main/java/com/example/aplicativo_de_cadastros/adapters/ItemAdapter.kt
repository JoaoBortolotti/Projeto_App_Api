package com.example.aplicativo_de_cadastros.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicativo_de_cadastros.models.Item
import com.example.aplicativo_de_cadastros.R

class ItemAdapter(
    private var items: MutableList<Item>,
    private val onItemClicked: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemDescription: TextView = itemView.findViewById(R.id.item_description)

        fun bind(item: Item) {
            itemName.text = item.name
            itemDescription.text = item.description
            itemView.setOnClickListener { onItemClicked(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_row, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    /**
     * Atualiza os itens de forma eficiente usando DiffUtil
     */
    fun updateItems(newItems: List<Item>) {
        val diffCallback = ItemDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        // Atualiza a lista de itens
        items.clear()
        items.addAll(newItems)

        // Notifica o RecyclerView para refletir as mudanças
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Classe que define como comparar os itens antigos e novos.
     */
    class ItemDiffCallback(
        private val oldList: List<Item>,
        private val newList: List<Item>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // Verifica se os itens são os mesmos, comparando os IDs
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // Verifica se os conteúdos dos itens são os mesmos
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
