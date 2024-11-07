package com.ktrajano.lendbook.models

data class User(
  val name: String? = null,
  var mail: String? = null,
  var password: String? = null,
  var age: Int? = null,
  var books: List<Book> = emptyList()
  )