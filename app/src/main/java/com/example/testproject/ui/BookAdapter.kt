package com.example.testproject.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.testproject.R
import com.example.testproject.databinding.ItemBookBinding
import com.example.testproject.model.Book

class BookAdapter(private val listener: LikeListener) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    private var bookList: List<Book> = emptyList()

    fun updateList(bookList: List<Book>) {
        this.bookList = bookList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = bookList[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    inner class ViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.run {
                bookName.text = book.name
                authorName.text = book.author
                price.text = "Price: ${book.price} $"
                favorite.setImageResource(
                    if (book.favorite)
                        R.drawable.ic_favorite_on
                    else
                        R.drawable.ic_favorite_off
                )
                bookImage.load(book.image)
                favorite.setOnClickListener {
                    listener.onLikeClicked(book)
                }
            }
        }
    }

    interface LikeListener {
        fun onLikeClicked(book: Book)
    }
}