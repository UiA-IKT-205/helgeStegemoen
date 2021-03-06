package com.example.mybooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybooks.books.Book
import com.example.mybooks.books.BookCollectionAdapter
import com.example.mybooks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    private var bookCollection: MutableList<Book> = mutableListOf(Book("Stephen King", "The Shining", 1977),
        Book("Ken Follet", "Eye of the Needle", 1978),
        Book("J. D. Salinger", "Catcher in the Rye", 1951))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Fikser feil på setContentView som gjorde at boklistingen ikke vistes
        // setContentView(R.layout.activity_main)
        setContentView(binding.root)

        // Løser problem med at boklisting ikke vistes for binding.bookListing.adapter = ...
        binding.bookListing.layoutManager = LinearLayoutManager(this)
        // bookListing er et recycler view
        binding.bookListing.adapter = BookCollectionAdapter(bookCollection)
    }
}