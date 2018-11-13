package me.chill.queries.library

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.*
import me.chill.utility.request.query

class CheckSavedTracksQuery private constructor(
  private val accessToken: String,
  private val ids: Set<String>) : AbstractQuery<Map<String, Boolean>>("me", "tracks", "contains") {

  override fun execute(): Map<String, Boolean> =
    gson.createResponseMap(ids, query(endpoint, accessToken, mapOf("ids" to ids.generateString())))

  class Builder(private val accessToken: String) {
    private val tracks = mutableSetOf<String>()

    fun addTracks(vararg tracks: String): Builder {
      this.tracks.splitAndAdd(tracks)
      return this
    }

    fun build(): CheckSavedTracksQuery {
      tracks.checkEmptyAndSizeLimit("Tracks", 50)

      return CheckSavedTracksQuery(accessToken, tracks)
    }
  }
}