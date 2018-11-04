package me.chill.queries.artist

import me.chill.models.Artist
import me.chill.queries.AbstractQuery
import me.chill.utility.request.query

class GetSingleArtistQuery private constructor(
  private val accessToken: String,
  private val artistId: String) : AbstractQuery<Artist>("artists", artistId) {

  override fun execute(): Artist = gson.fromJson(query(endpoint, accessToken).text, Artist::class.java)

  class Builder(private val accessToken: String, private val artistId: String) {
    fun build() = GetSingleArtistQuery(accessToken, artistId)
  }
}