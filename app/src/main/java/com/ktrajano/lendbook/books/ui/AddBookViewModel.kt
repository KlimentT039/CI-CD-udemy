package com.ktrajano.lendbook.books.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ktrajano.lendbook.books.service.AddBooksService
import com.ktrajano.lendbook.models.ApiResult
import com.ktrajano.lendbook.models.ListOfBooks
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class AddBookViewModel(val service: AddBooksService) : ViewModel() {

  private val _books = MutableSharedFlow<ApiResult<ListOfBooks>>()
  val books: SharedFlow<ApiResult<ListOfBooks>> = _books

  suspend fun getBooksByTitle(title: String) {
    viewModelScope.launch {
      _books.emit(ApiResult.Loading(isLoading = true))
      val response = service.getBooksByTitle(title)
      if (response.isSuccessful) {
        _books.emit(ApiResult.Success(response.body()))
      } else {
        _books.emit(ApiResult.Error("There is an error with your request"))
      }
    }
  }
}