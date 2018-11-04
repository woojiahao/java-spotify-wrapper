package me.chill.models

import com.google.gson.annotations.SerializedName

data class Playlist(
  val collaborative: Boolean,
  val description: String,
  val externalUrls: Map<String, String>,
  val followers: Followers,
  @SerializedName("href") val playlistDetailsUrl: String,
  val id: String,
  val images: List<Image>,
  val name: String,
  val owner: User,
  val public: Boolean?,
  val snapshotId: String,
  val tracks: Paging<Track>,
  val type: String,
  @SerializedName("uri") val playlistSpotifyUri: String
)