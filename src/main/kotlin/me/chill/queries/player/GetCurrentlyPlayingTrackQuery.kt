package me.chill.queries.player

import com.neovisionaries.i18n.CountryCode
import me.chill.models.CurrentlyPlaying
import me.chill.queries.AbstractQuery
import me.chill.utility.request.query

class GetCurrentlyPlayingTrackQuery private constructor(
  private val accessToken: String,
  private val market: String?) : AbstractQuery<CurrentlyPlaying?>("me", "player", "currently-playing") {

  override fun execute(): CurrentlyPlaying? {
    val parameters = mapOf("market" to market)
    val response = query(queryEndpoint, accessToken, parameters)

    response.statusCode.takeIf { it == 204 }?.let { return null }

    return gson.fromJson(response.text, CurrentlyPlaying::class.java)
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