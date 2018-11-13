package me.chill.queries.artist

import me.chill.models.Artist
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmpty
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.generateString
import me.chill.utility.extensions.readFromJsonArray
import me.chill.utility.request.query

class GetSeveralArtistsQuery private constructor(
  private val accessToken: String,
  private val ids: String) : AbstractQuery<List<Artist>>("artists") {

  override fun execute() = gson.readFromJsonArray<Artist>("artists", query(endpoint, accessToken, mapOf("ids" to ids)))

  class Builder(private val accessToken: String) {
    private val artists = mutableListOf<String>()

    fun addArtists(vararg artists: String): Builder {
      this.artists.addAll(artists)
      return this
    }

    fun build(): GetSeveralArtistsQuery {
      artists.checkEmpty("Artists")
      artists.checkListSizeLimit("Artists", 50)

      return GetSeveralArtistsQuery(accessToken, artists.generateString())
    }
  }
}