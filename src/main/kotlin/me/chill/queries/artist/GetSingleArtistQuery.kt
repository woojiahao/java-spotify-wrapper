package me.chill.queries.artist

import me.chill.models.Artist
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.RequestMethod

class GetSingleArtistQuery private constructor(
  private val accessToken: String,
  private val artistId: String) : AbstractQuery<Artist>(accessToken, RequestMethod.Get, "artists", artistId) {

  override fun execute(): Artist =
    gson.read(checkedQuery().text)

  class Builder(private val accessToken: String, private val artistId: String) {
    fun build() = GetSingleArtistQuery(accessToken, artistId)
  }
}