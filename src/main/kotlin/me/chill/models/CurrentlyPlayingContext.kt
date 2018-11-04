package me.chill.models

import com.google.gson.annotations.SerializedName

data class CurrentlyPlayingContext(
  val device: Device,
  val repeatState: RepeatState,
  val shuffleState: Boolean,
  val context: Context,
  val timestamp: Long,
  @SerializedName("progress_ms") val progress: Int?,
  val isPlaying: Boolean,
  @SerializedName("item") val track: Track?,
  val currentlyPlayingType: PlayingType
)