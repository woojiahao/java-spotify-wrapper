package me.chill.queries.artist

import com.neovisionaries.i18n.CountryCode
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.Track
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.readFromJsonArray
import me.chill.utility.request.RequestMethod

class GetArtistTopTracksQuery private constructor(
  private val accessToken: String,
  private val artistId: String,
  private val market: String) : AbstractQuery<List<Track>>(accessToken, RequestMethod.Get, "artists", artistId, "top-tracks") {

  override fun execute(): List<Track> =
    gson.readFromJsonArray("tracks", checkedQuery(mapOf("market" to market)))

  class Builder(private val accessToken: String, private val artistId: String) {
    private var market: CountryCode? = null

    fun market(market: CountryCode): Builder {
      this.market = market
      return this
    }

    fun build(): GetArtistTopTracksQuery {
      market
        ?: throw SpotifyQueryException("You must specify the market in order to retrieve the artist's top tracks")
      return GetArtistTopTracksQuery(accessToken, artistId, market!!.alpha2)
    }
  }
}