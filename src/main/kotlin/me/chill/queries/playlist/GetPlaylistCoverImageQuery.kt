package me.chill.queries.playlist

import com.google.gson.JsonArray
import me.chill.models.Image
import me.chill.queries.AbstractQuery
import me.chill.utility.request.query

class GetPlaylistCoverImageQuery private constructor(
  private val accessToken: String,
  private val playlistId: String) : AbstractQuery<List<Image>>("playlists", playlistId, "images") {

  override fun execute() =
    gson
      .fromJson(query(endpoint, accessToken).text, JsonArray::class.java)
      .map { gson.fromJson(it, Image::class.java) }

  class Builder(private val accessToken: String, private val playlistId: String) {
    fun build() = GetPlaylistCoverImageQuery(accessToken, playlistId)
  }
}