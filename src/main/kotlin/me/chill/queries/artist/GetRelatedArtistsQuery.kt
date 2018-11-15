package me.chill.queries.artist

import me.chill.models.Artist
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.readFromJsonArray
import me.chill.utility.request.RequestMethod

class GetRelatedArtistsQuery private constructor(
  private val accessToken: String,
  private val artistId: String) : AbstractQuery<List<Artist>>(accessToken, RequestMethod.Get, "artists", artistId, "related-artists") {

  // TODO: Check this
  override fun execute(): List<Artist> =
    gson.readFromJsonArray("artists", checkedQuery())

  class Builder(private val accessToken: String, private val artistId: String) {
    fun build() = GetRelatedArtistsQuery(accessToken, artistId)
  }
}