package com.ktrajano.lendbook.utils

import com.ktrajano.lendbook.models.ListOfBooks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleRestApi {

  @GET("books/v1/volumes")
  suspend fun searchBooks(
    @Query("q") title: String,
    @Query("maxResults") results: Int = 20
  ) : Response<ListOfBooks>

}