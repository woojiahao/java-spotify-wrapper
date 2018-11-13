package me.chill.models

import com.google.gson.annotations.SerializedName

data class AudioFeatures(
  @SerializedName("duration_ms") val duration: Int,
  val key: Int,
  val mode: Int,
  val timeSignature: Int,
  val acousticness: Double,
  val danceability: Double,
  val energy: Double,
  val instrumentalness: Double,
  val liveness: Double,
  val loudness: Double,
  val speechiness: Double,
  val valence: Double,
  val tempo: Double,
  val id: String,
  @SerializedName("uri") val audioFeaturesSpotifyUri: String,
  @SerializedName("track_href") val audioFeaturesDetailsUrl: String,
  val analysisUrl: String,
  val type: String
)