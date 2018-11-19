package me.chill.queries.track

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Track
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.RequestMethod

class GetSingleTrackQuery private constructor(
  private val accessToken: String,
  private val trackId: String,
  private val market: String?) : AbstractQuery<Track>(accessToken, RequestMethod.Get, "tracks", trackId) {

  override fun execute(): Track =
    gson.read(checkedQuery(mapOf("market" to market)).text)

  class Builder(private val accessToken: String, private val trackId: String) {
    private var market: CountryCode? = null

    fun market(market: CountryCode): Builder {
      this.market = market
      return this
    }

    fun build() = GetSingleTrackQuery(accessToken, trackId, market?.alpha2)
  }
}