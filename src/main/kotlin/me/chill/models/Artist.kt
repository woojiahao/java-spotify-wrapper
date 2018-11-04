package me.chill.models

import com.google.gson.annotations.SerializedName

data class Artist(
  val externalUrls: Map<String, String>,
  val followers: Followers,
  val genres: List<String>,
  @SerializedName("href") val artistDetailsUrl: String,
  val id: String,
  val images: List<Image>,
  val name: String,
  val popularity: Int,
  val type: String,
  @SerializedName("uri") val artistSpotifyUri: String
)