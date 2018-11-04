package me.chill.models

data class PlayHistory(
  val track: Track,
  val playedAt: String,
  val context: Context
)