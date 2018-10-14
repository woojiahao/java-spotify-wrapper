package me.chill.models

import com.google.gson.annotations.SerializedName

data class LinkedTrack(
	val externalUrls: Map<String, String>,
	@SerializedName("href") val linkedTrackDetailsUrl: String,
	val id: String,
	val type: String,
	@SerializedName("uri") val linkedTrackSpotifyUri: String
)