package me.chill.queries.library

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.generateNullableString
import me.chill.utility.request.put

class SaveTracksQuery private constructor(
  private val accessToken: String,
  private val ids: String?) : AbstractQuery<Boolean>("me", "albums") {

  override fun execute() = put(endpoint, accessToken, mapOf("ids" to ids)).statusCode == 200

  class Builder(private val accessToken: String) {
    private val tracks = mutableListOf<String>()

    fun addTracks(vararg tracks: String): Builder {
      this.tracks.addAll(tracks)
      return this
    }

    fun build(): SaveTracksQuery {
      tracks.checkListSizeLimit("Tracks", 50)

      return SaveTracksQuery(accessToken, tracks.generateNullableString())
    }

  }
}