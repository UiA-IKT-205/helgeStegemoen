package com.example.mybooks.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.databinding.BookLayoutBinding

// Dette er en recycler adapter
class BookCollectionAdapter(private val books: MutableList<Book>): RecyclerView.Adapter<BookCollectionAdapter.ViewHolder>() {

    class ViewHolder(val binding:BookLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(book: Book){
            binding.author.text = book.author
            binding.title.text = book.title
            binding.published.text = book.published.toString()
        }
    }

    override fun getItemCount(): Int = books.size

    // Knytter dataene opp til view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    // Skaper the kortet (Card) som vi ønsker
    // Trenger ikke være Card, kan returnere forskjellige views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(BookLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}