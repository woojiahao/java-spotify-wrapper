package me.chill.queries.player

import com.neovisionaries.i18n.CountryCode
import me.chill.models.CurrentlyPlaying
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.RequestMethod

class GetCurrentlyPlayingTrackQuery private constructor(
  private val accessToken: String,
  private val market: String?) : AbstractQuery<CurrentlyPlaying?>(accessToken, RequestMethod.Get, "me", "player", "currently-playing") {

  override fun execute(): CurrentlyPlaying? {
    val response = query(mapOf("market" to market))

    if (response.statusCode == 204) return null

    return gson.read(response.text)
  }

  class Builder(private val accessToken: String) {
    private var market: CountryCode? = null

    fun market(market: CountryCode): Builder {
      this.market = market
      return this
    }

    fun build() = GetCurrentlyPlayingTrackQuery(accessToken, market?.alpha2)
  }
}