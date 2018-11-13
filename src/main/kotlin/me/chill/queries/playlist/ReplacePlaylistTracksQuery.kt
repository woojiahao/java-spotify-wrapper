package me.chill.queries.playlist

import khttp.put
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.checkSizeLimit
import me.chill.utility.extensions.conditionalMap
import me.chill.utility.extensions.splitAndAdd
import me.chill.utility.request.generateModificationHeader
import me.chill.utility.request.responseCheck

class ReplacePlaylistTracksQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val uris: Set<String>) : AbstractQuery<Boolean>("playlists", playlistId, "tracks") {

  override fun execute(): Boolean {
    val fixed = uris.conditionalMap({ !it.startsWith("spotify:track:") }) {
      "spotify:track:$it"
    }
    val body = gson.toJson(mapOf("uris" to fixed))

    val response = put(endpoint, generateModificationHeader(accessToken), data = body)
    response.responseCheck()

    return response.statusCode == 201
  }

  class Builder(private val accessToken: String, private val playlistId: String) {
    private val uris = mutableSetOf<String>()

    fun addUris(vararg uris: String): Builder {
      this.uris.splitAndAdd(uris)
      return this
    }

    fun build(): ReplacePlaylistTracksQuery {
      uris.checkSizeLimit("Uri", 100)

      return ReplacePlaylistTracksQuery(accessToken, playlistId, uris)
    }
  }
}