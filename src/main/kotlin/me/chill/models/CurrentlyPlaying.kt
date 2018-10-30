package me.chill.models

import com.google.gson.annotations.SerializedName

data class CurrentlyPlaying(
	val context: Context,
	val timestamp: Long,
	@SerializedName("progress_ms") val progress: Int?,
	val isPlaying: Boolean,
	val item: Track?,
	val currentlyPlayingType: PlayingType
)