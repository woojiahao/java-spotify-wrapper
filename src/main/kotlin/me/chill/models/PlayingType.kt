package me.chill.models

import com.google.gson.annotations.SerializedName

enum class PlayingType {
  @SerializedName("track") Track,
  @SerializedName("episode") Episode,
  @SerializedName("ad") Ad,
  @SerializedName("unknown") Unknown;
}
