package me.chill.models

import com.google.gson.annotations.SerializedName

data class Followers(
	@SerializedName("href") val followersDetailUrl: String?,
	val total: Int
)
