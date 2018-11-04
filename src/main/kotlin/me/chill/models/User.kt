package me.chill.models

import com.google.gson.annotations.SerializedName

data class User(
	val birthdate: String?,
	val country: String?,
	val displayName: String?,
	val email: String?,
	val externalUrls: Map<String, String>,
	val followers: Followers,
	@SerializedName("href") val userDetailsUrl: String,
	val id: String,
	val images: List<Image>,
	val product: String?,
	val type: String,
	@SerializedName("uri") val userSpotifyUri: String
)