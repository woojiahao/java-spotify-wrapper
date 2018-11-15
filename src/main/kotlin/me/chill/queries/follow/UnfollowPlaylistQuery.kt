package me.chill.queries.follow

import me.chill.queries.AbstractQuery
import me.chill.utility.request.RequestMethod

class UnfollowPlaylistQuery private constructor(
  private val accessToken: String,
  private val playlistId: String) : AbstractQuery<Boolean>(accessToken, RequestMethod.Delete, "playlists", playlistId, "followers") {

  override fun execute() =
    checkedQuery().statusCode == 200

  class Builder(private val accessToken: String, private val playlistId: String) {
    fun build() = UnfollowPlaylistQuery(accessToken, playlistId)
  }
}