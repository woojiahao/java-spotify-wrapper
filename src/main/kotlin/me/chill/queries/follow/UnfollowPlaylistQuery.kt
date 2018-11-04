package me.chill.queries.follow

import khttp.delete
import me.chill.queries.AbstractQuery
import me.chill.utility.request.generateHeader
import me.chill.utility.request.responseCheck

class UnfollowPlaylistQuery private constructor(
  private val accessToken: String,
  private val playlistId: String) : AbstractQuery<Boolean>("playlists", playlistId, "followers") {

  override fun execute(): Boolean {
    val response = delete(endpoint, generateHeader(accessToken))
    response.responseCheck()

    return response.statusCode == 200
  }

  class Builder(private val accessToken: String, private val playlistId: String) {
    fun build() = UnfollowPlaylistQuery(accessToken, playlistId)
  }
}