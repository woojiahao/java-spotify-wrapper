package me.chill.queries.artist

import me.chill.models.Artist
import me.chill.queries.AbstractQuery
import me.chill.utility.request.query
import me.chill.utility.request.readFromJsonArray

class GetRelatedArtistsQuery private constructor(
  private val accessToken: String,
  private val artistId: String) : AbstractQuery<List<Artist>>("artists", artistId, "related-artists") {

  override fun execute() = gson.readFromJsonArray<Artist>("artists", query(endpoint, accessToken))

  class Builder(private val accessToken: String, private val artistId: String) {
    fun build() = GetRelatedArtistsQuery(accessToken, artistId)
  }
}