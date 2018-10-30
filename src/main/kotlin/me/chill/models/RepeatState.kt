package me.chill.models

import com.google.gson.annotations.SerializedName

enum class RepeatState {
	@SerializedName("off") Off,
	@SerializedName("track") Track,
	@SerializedName("context") Context;
}
