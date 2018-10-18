package me.chill.models

import com.google.gson.annotations.SerializedName

data class PublicUser(
	val displayName: String,
	val externalUrls: Map<String, String>,
	val followers: Followers,
	@SerializedName("href") val publicUserDetailsUrl: String,
	val id: String,
	val image: List<Image>,
	val type: String,
	@SerializedName("uri") val publicUserSpotifyUri: String
)