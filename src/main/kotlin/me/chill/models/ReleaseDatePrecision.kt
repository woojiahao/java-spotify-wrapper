package me.chill.models

import com.google.gson.annotations.SerializedName

enum class ReleaseDatePrecision {
	@SerializedName("year") Year,
	@SerializedName("month") Month,
	@SerializedName("day") Day
}