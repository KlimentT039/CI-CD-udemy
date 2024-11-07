package com.ktrajano.lendbook.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("error")
data class GoogleErrorBody(
  @JsonProperty("code") val code: Int? = null,
  @JsonProperty("message") val message: String? = null,
  val errorType: Errors? =null
)

@JsonRootName("errors")
data class Errors(
  @JsonProperty("message")
  val message: String? = null,
  @JsonProperty("domain")
  val domain: String? = null,
  @JsonProperty("reason")
  val reason: String? = null
)