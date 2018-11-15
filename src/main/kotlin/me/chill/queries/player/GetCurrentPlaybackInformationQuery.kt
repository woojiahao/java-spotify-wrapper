package me.chill.queries.player

import com.neovisionaries.i18n.CountryCode
import me.chill.models.CurrentlyPlayingContext
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.RequestMethod

class GetCurrentPlaybackInformationQuery private constructor(
  private val accessToken: String,
  private val market: String?) : AbstractQuery<CurrentlyPlayingContext?>(accessToken, RequestMethod.Get, "me", "player") {

  override fun execute(): CurrentlyPlayingContext? {
    val response = checkedQuery(mapOf("market" to market))

    if (response.text.isEmpty()) return null

    return gson.read(response.text)
  }

  class Builder(private val accessToken: String) {
    private var market: CountryCode? = null

    fun market(market: CountryCode): Builder {
      this.market = market
      return this
    }

    fun build() = GetCurrentPlaybackInformationQuery(accessToken, market?.alpha2)
  }
}