package me.chill.models

import com.google.gson.annotations.SerializedName

data class Paging<T>(
  @SerializedName("href") val fullResultUrl: String,
  val items: List<T>,
  val limit: Int,
  val next: String,
  val offset: Int,
  val previous: String,
  val total: Int
)