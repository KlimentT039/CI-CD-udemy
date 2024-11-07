package com.ktrajano.lendbook.books.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ktrajano.lendbook.R
import com.ktrajano.lendbook.databinding.BookSearchResultsBinding
import com.ktrajano.lendbook.models.Book
import com.ktrajano.lendbook.models.ListOfBooks
import com.ktrajano.lendbook.models.VolumeInfo
import com.ktrajano.lendbook.utils.Constants
import com.squareup.picasso.Picasso

class BooksAdapter(val books: ListOfBooks, private val clickListener: () -> Unit) :
  RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

  class ViewHolder(private val views: BookSearchResultsBinding) :
    RecyclerView.ViewHolder(views.root), OnClickListener {

    fun bind(book: Book) {
      views.bookTitle.text = book.title
      views.bookAuthors.text = book.authors.toString().replace("[", "").replace("]", "")
      views.pageCount.text = book.pageCount.toString()
      Picasso.get().load(book.imageLinks?.thumbnail).into(views.bookCover)
      if (bindingAdapterPosition == 0) {
        itemView.setBackgroundResource(R.drawable.selected_background)
      } else {
        itemView.setBackgroundResource(R.drawable.default_background)
      }
    }

    override fun onClick(p0: View?) {
      TODO("Not yet implemented")
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = BookSearchResultsBinding.inflate(
      LayoutInflater.from(parent.context), null, false
    )
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(books.list[position].volumeInfo)
  }

  override fun getItemCount(): Int {
    return books.list.size
  }

}