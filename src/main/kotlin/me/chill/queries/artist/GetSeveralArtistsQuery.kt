package me.chill.queries.artist

import me.chill.models.Artist
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmptyAndSizeLimit
import me.chill.utility.extensions.generateString
import me.chill.utility.extensions.readFromJsonArray
import me.chill.utility.extensions.splitAndAdd
import me.chill.utility.request.RequestMethod

class GetSeveralArtistsQuery private constructor(
  private val accessToken: String,
  private val ids: Set<String>) : AbstractQuery<List<Artist>>(accessToken, RequestMethod.Get, "artists") {

  override fun execute(): List<Artist> =
    gson.readFromJsonArray("artists", checkedQuery(mapOf("ids" to ids.generateString())))

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