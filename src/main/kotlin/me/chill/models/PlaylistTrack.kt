package me.chill.models

data class PlaylistTrack(
  val addedAt: String?,
  val addedBy: User?,
  val isLocal: Boolean,
  val track: Track
)