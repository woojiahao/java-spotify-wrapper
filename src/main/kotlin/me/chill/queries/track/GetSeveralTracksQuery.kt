package me.chill.queries.track

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Track
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmptyAndSizeLimit
import me.chill.utility.extensions.generateString
import me.chill.utility.extensions.readFromJsonArray
import me.chill.utility.extensions.splitAndAdd
import me.chill.utility.request.RequestMethod

class GetSeveralTracksQuery private constructor(
  private val accessToken: String,
  private val tracks: Set<String>,
  private val market: String?) : AbstractQuery<List<Track>>(accessToken, RequestMethod.Get, "tracks") {

  override fun execute(): List<Track> {
    val parameters = mapOf(
      "ids" to tracks.generateString(),
      "market" to market
    )
    return gson.readFromJsonArray("tracks", checkedQuery(parameters))
  }

  class Builder(private val accessToken: String) {
    private val tracks = mutableSetOf<String>()
    private var market: CountryCode? = null

    fun addTracks(vararg tracks: String): Builder {
      this.tracks.splitAndAdd(tracks)
      return this
    }

    fun market(market: CountryCode): Builder {
      this.market = market
      return this
    }

    fun build(): GetSeveralTracksQuery {
      tracks.checkEmptyAndSizeLimit("Tracks", 50)

      return GetSeveralTracksQuery(accessToken, tracks, market?.alpha2)
    }
  }
}

