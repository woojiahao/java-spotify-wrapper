package me.chill.queries.playlist

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkSizeLimit
import me.chill.utility.extensions.conditionalMap
import me.chill.utility.extensions.splitAndAdd
import me.chill.utility.request.RequestMethod

class ReplacePlaylistTracksQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val uris: Set<String>) : AbstractQuery<Boolean>(accessToken, RequestMethod.Put, "playlists", playlistId, "tracks") {

  override fun execute(): Boolean {
    val fixed = uris.conditionalMap({ !it.startsWith("spotify:track:") }) { "spotify:track:$it" }
    val body = gson.toJson(mapOf("uris" to fixed))

    return checkedQuery(data = body, isModification = true).statusCode == 201
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