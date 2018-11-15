package me.chill.queries.follow

import me.chill.queries.AbstractQuery
import me.chill.utility.request.RequestMethod

class FollowPlaylistQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val public: Boolean) : AbstractQuery<Boolean>(accessToken, RequestMethod.Put, "playlists", playlistId, "followers") {

  // TODO: Check this
  override fun execute(): Boolean {
    val body = gson.toJson(mapOf(
      "public" to public
    ))

    return checkedQuery(data = body, isModification = true).statusCode == 200
  }

  class Builder(private val accessToken: String, private val playlistId: String) {
    private var public = true

    fun public(public: Boolean): Builder {
      this.public = public
      return this
    }

    fun build() = FollowPlaylistQuery(accessToken, playlistId, public)
  }
}