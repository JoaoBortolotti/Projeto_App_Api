package com.example.aplicativo_de_cadastros.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicativo_de_cadastros.R
import com.example.aplicativo_de_cadastros.model.Post

class PostAdapter(private var posts: List<Post>, private val context: Context) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val bodyTextView: TextView = itemView.findViewById(R.id.textViewBody)

        init {
            itemView.setOnClickListener {
                val post = posts[adapterPosition]
                val intent = Intent(context, DetalheActivity::class.java)
                intent.putExtra("post", post)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.titleTextView.text = post.title
        holder.bodyTextView.text = post.body
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePosts(newPosts: List<Post>) {
        posts = newPosts
        notifyDataSetChanged()
    }
}
