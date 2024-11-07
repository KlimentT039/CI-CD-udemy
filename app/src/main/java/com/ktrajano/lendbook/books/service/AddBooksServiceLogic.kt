package com.ktrajano.lendbook.books.service

import com.ktrajano.lendbook.models.ListOfBooks
import com.ktrajano.lendbook.utils.GoogleRestApi
import retrofit2.Response

class AddBooksServiceLogic(private val googleRestApi: GoogleRestApi) : AddBooksService {

  override suspend fun getBooksByTitle(title: String) : Response<ListOfBooks> {
    return googleRestApi.searchBooks(title)
  }
}