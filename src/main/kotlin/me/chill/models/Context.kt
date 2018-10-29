package me.chill.models

import com.google.gson.annotations.SerializedName

enum class ContextType {
	@SerializedName("album") Album,
	@SerializedName("artist") Artist,
	@SerializedName("playlist") Playlist;
}

data class Context(
	@SerializedName("uri") val contextDetailsUri: String,
	@SerializedName("href") val contextLink: String?,
	val externalUrls: Map<String, String>,
	val type: ContextType
)