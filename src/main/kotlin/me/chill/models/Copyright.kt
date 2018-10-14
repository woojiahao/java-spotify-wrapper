package me.chill.models

import com.google.gson.annotations.SerializedName

enum class CopyrightType(val description: String) {
	C("The Copyright"), P("The Sound Recording (Performance) Copyright")
}

data class Copyright(
	@SerializedName("text") val copyrightText: String,
	@SerializedName("type") val copyrightType: CopyrightType
)