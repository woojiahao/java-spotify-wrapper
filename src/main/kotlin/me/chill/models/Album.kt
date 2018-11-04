package me.chill.models

import com.google.gson.annotations.SerializedName

/**
 *
 */
data class Album(
  val albumType: String,
  val artists: List<Artist>,
  val availableMarkets: List<String>,
  val copyrights: List<Copyright>,
  val externalIds: Map<String, String>,
  val externalUrls: Map<String, String>,
  val genres: List<String>,
  @SerializedName("href") val albumDetailsUrl: String,
  val id: String,
  val images: List<Image>,
  val label: String,
  val name: String,
  val popularity: Int,
  val releaseDate: String,
  val releaseDatePrecision: ReleaseDatePrecision,
  val tracks: Paging<Track>,
  val type: String,
  @SerializedName("uri") val albumSpotifyUri: String
)