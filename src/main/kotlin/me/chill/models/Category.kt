package me.chill.models

import com.google.gson.annotations.SerializedName

data class Category(
	@SerializedName("href") val categoryDetailUrl: String,
	val icons: List<Image>,
	val id: String,
	val name: String
)