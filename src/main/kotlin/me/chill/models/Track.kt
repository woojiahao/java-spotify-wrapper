package me.chill.models

import com.google.gson.annotations.SerializedName

data class Track(
  val album: Album,
  val artist: List<Artist>,
  val availableMarkets: List<String>,
  val discNumber: Int,
  @SerializedName("duration_ms") val duration: Int,
  val explicit: Boolean,
  val externalIds: Map<String, String>,
  val externalUrls: Map<String, String>,
  @SerializedName("href") val trackDetailsUrl: String,
  val id: String,
  val isPlayable: Boolean,
  val linkedFrom: LinkedTrack,
  val restrictions: Restrictions,
  val name: String,
  val popularity: Int,
  val previewUrl: String?,
  val trackNumber: Int,
  val type: String,
  @SerializedName("uri") val trackSpotifyUri: String,
  val isLocal: Boolean
)