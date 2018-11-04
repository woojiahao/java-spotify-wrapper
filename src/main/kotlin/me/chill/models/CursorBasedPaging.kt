package me.chill.models

import com.google.gson.annotations.SerializedName

data class CursorBasedPaging<T>(
  @SerializedName("href") val fullResult: String,
  val items: List<T>,
  val limit: Int,
  val next: String?,
  val cursor: Cursor,
  val total: Int
)