package me.chill.queries.artist

import com.neovisionaries.i18n.CountryCode
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.Track
import me.chill.queries.AbstractQuery
import me.chill.utility.request.query
import me.chill.utility.request.readFromJsonArray

class GetArtistTopTracksQuery private constructor(
  private val id: String,
  private val accessToken: String,
  private val market: String) : AbstractQuery<List<Track>>("artists", id, "top-tracks") {

  override fun execute(): List<Track> {
    val parameters = mapOf("market" to market)

    val response = query(queryEndpoint, accessToken, parameters)

    return gson.readFromJsonArray("tracks", response)
  }

  class Builder(private val id: String, private val accessToken: String) {
    private var market: CountryCode? = null

    fun market(market: CountryCode): Builder {
      this.market = market
      return this
    }

    fun build(): GetArtistTopTracksQuery {
      market
        ?: throw SpotifyQueryException("You must specify the market in order to retrieve the artist's top tracks")
      return GetArtistTopTracksQuery(id, accessToken, market!!.alpha2)
    }
  }
}