package me.chill.queries.follow

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.generateParameters
import me.chill.utility.request.Header
import me.chill.utility.request.responseCheck

class FollowPlaylistQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val public: Boolean) : AbstractQuery<Boolean>("playlists", playlistId, "followers") {

  override fun execute(): Boolean {
    val body = mapOf(
      "public" to public
    ).generateParameters()

    val headers = Header.Builder()
      .accessToken(accessToken)
      .contentType(Header.Builder.ContentType.Json)
      .build()
      .generate()

    val response = khttp.put(endpoint, headers, data = body)
    response.responseCheck()

    return response.statusCode == 200
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