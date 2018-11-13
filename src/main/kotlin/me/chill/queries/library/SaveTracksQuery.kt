package me.chill.queries.library

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.checkSizeLimit
import me.chill.utility.extensions.generateNullableString
import me.chill.utility.extensions.splitAndAdd
import me.chill.utility.request.put

class SaveTracksQuery private constructor(
  private val accessToken: String,
  private val ids: Set<String>) : AbstractQuery<Boolean>("me", "albums") {

  override fun execute() = put(endpoint, accessToken, mapOf("ids" to ids.generateNullableString())).statusCode == 200

  class Builder(private val accessToken: String) {
    private val tracks = mutableSetOf<String>()

    fun addTracks(vararg tracks: String): Builder {
      this.tracks.splitAndAdd(tracks)
      return this
    }

    fun build(): SaveTracksQuery {
      tracks.checkSizeLimit("Tracks", 50)

      return SaveTracksQuery(accessToken, tracks)
    }

  }
}