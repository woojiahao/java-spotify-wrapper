package me.chill.models

import com.google.gson.annotations.SerializedName

enum class AlbumType {
  @SerializedName("album") Album,
  @SerializedName("single") Single,
  @SerializedName("compilation") Compilation
}