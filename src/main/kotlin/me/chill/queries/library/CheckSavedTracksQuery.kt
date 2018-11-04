package me.chill.queries.library

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmpty
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.generateString
import me.chill.utility.request.createCheckMap
import me.chill.utility.request.query

class CheckSavedTracksQuery private constructor(
  private val accessToken: String,
  private val ids: String) : AbstractQuery<Map<String, Boolean>>("me", "tracks", "contains") {

  override fun execute() = query(endpoint, accessToken, mapOf("ids" to ids)).createCheckMap(ids)

  class Builder(private val accessToken: String) {
    private val tracks = mutableListOf<String>()

    fun addTrack(track: String): Builder {
      tracks.add(track)
      return this
    }

    fun setTracks(tracks: List<String>): Builder {
      this.tracks.clear()
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