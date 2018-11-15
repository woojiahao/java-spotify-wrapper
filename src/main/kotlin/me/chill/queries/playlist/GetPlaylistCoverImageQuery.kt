package me.chill.queries.playlist

import me.chill.models.Image
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.readArray
import me.chill.utility.request.RequestMethod

class GetPlaylistCoverImageQuery private constructor(
  private val accessToken: String,
  private val playlistId: String) : AbstractQuery<List<Image>>(accessToken, RequestMethod.Get, "playlists", playlistId, "images") {

  override fun execute(): List<Image> =
    gson.readArray(checkedQuery())

  class Builder(private val accessToken: String, private val playlistId: String) {
    fun build() = GetPlaylistCoverImageQuery(accessToken, playlistId)
  }
}