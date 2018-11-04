package me.chill.models

import com.google.gson.annotations.SerializedName

data class RecommendationSeed(
  @SerializedName("afterFilteringSize") val afterFilteringSize: Int?,
  @SerializedName("afterRelinkingSize") val afterRelinkingSize: Int?,
  @SerializedName("href") val seedDetailsUrl: String?,
  val id: String?,
  @SerializedName("initialPoolSize") val initialPoolSize: Int?,
  val type: String?
)