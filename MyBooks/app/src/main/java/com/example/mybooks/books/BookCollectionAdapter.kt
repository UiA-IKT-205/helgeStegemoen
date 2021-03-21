package com.example.mybooks.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.databinding.BookLayoutBinding

// Dette er en recycler adapter. onBookClicked trenger clickable=yes i book_layout.xml.
class BookCollectionAdapter(private val books: MutableList<Book>,
                            private val onBookClicked:(Book)->Unit)
    : RecyclerView.Adapter<BookCollectionAdapter.ViewHolder>() {

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

    public fun updateCollection(newBooks:List<Book>){   // Trenger ikke være mutable
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()  // Funksjon som er del av alle adapter, som sier nå må du oppdatere
    }
}