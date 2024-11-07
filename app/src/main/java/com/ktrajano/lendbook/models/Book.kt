package com.ktrajano.lendbook.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

data class ListOfBooks(@JsonProperty("items") val list: ArrayList<VolumeInfo>)

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class VolumeInfo(
  @JsonProperty("id") val id: String,
  @JsonProperty("volumeInfo") val volumeInfo: Book
) : Parcelable

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class Book(
  @JsonProperty("title")
  val title: String?,
  @JsonProperty("authors")
  val authors: List<String>?,
  @JsonProperty("description")
  val description: String?,
  @JsonProperty("pageCount")
  val pageCount: Int?,
  @JsonProperty("categories")
  val categories: List<String>?,
  @JsonProperty("imageLinks")
  val imageLinks: Thumbnail?,
) : Parcelable

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class Thumbnail(
  @JsonProperty("thumbnail")
  val thumbnail: String? = ""
) : Parcelable