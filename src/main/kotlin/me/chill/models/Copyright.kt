package me.chill.models

import com.google.gson.annotations.SerializedName

data class Copyright(
  @SerializedName("text") val copyrightText: String,
  @SerializedName("type") val copyrightType: CopyrightType
)