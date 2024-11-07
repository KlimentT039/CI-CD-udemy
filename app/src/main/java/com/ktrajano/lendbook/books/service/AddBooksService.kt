package com.ktrajano.lendbook.books.service

import com.ktrajano.lendbook.models.ListOfBooks
import retrofit2.Response

interface AddBooksService {

 suspend fun getBooksByTitle(title: String) : Response<ListOfBooks>

}