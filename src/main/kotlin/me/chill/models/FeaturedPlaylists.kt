package me.chill.models

data class FeaturedPlaylists(
  val message: String,
  val playlists: Paging<Playlist>
)