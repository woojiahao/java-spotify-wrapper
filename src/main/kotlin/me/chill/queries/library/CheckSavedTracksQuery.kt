package me.chill.queries.library

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmpty
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.createResponseMap
import me.chill.utility.extensions.generateString
import me.chill.utility.request.query

class CheckSavedTracksQuery private constructor(
  private val accessToken: String,
  private val ids: String) : AbstractQuery<Map<String, Boolean>>("me", "tracks", "contains") {

  override fun execute(): Map<String, Boolean> =
    gson.createResponseMap(ids.split(","), query(endpoint, accessToken, mapOf("ids" to ids)))

  class Builder(private val accessToken: String) {
    private val tracks = mutableListOf<String>()

    fun addTracks(vararg tracks: String): Builder {
      this.tracks.addAll(tracks)
      return this
    }

    fun build(): CheckSavedTracksQuery {
      tracks.checkEmpty("Tracks")
      tracks.checkListSizeLimit("Tracks", 50)

      return CheckSavedTracksQuery(accessToken, tracks.generateString())
    }
  }
}