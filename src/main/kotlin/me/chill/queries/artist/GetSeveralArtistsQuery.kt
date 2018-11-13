package me.chill.queries.artist

import me.chill.models.Artist
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.*
import me.chill.utility.request.query

class GetSeveralArtistsQuery private constructor(
  private val accessToken: String,
  private val ids: Set<String>) : AbstractQuery<List<Artist>>("artists") {

  override fun execute() = gson.readFromJsonArray<Artist>("artists", query(endpoint, accessToken, mapOf("ids" to ids.generateString())))

  class Builder(private val accessToken: String) {
    private val artists = mutableSetOf<String>()

    fun addArtists(vararg artists: String): Builder {
      this.artists.splitAndAdd(artists)
      return this
    }

    fun build(): GetSeveralArtistsQuery {
      artists.checkEmptyAndSizeLimit("Artists", 50)

      return GetSeveralArtistsQuery(accessToken, artists)
    }
  }
}