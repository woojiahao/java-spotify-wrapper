package me.chill.queries.playlist

import com.neovisionaries.i18n.CountryCode
import khttp.get
import me.chill.models.Paging
import me.chill.models.PlaylistTrack
import me.chill.models.Track
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLimit
import me.chill.utility.extensions.checkOffset
import me.chill.utility.extensions.generateParameters
import me.chill.utility.extensions.read
import me.chill.utility.request.generateHeader
import me.chill.utility.request.query
import me.chill.utility.request.responseCheck

class GetPlaylistTracksQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val limit: Int,
  private val offset: Int,
  private val market: String?) : AbstractQuery<Paging<PlaylistTrack>?>("playlists", playlistId, "tracks") {

  override fun execute(): Paging<PlaylistTrack>? {
    val parameters = mapOf(
      "limit" to limit,
      "offset" to offset,
      "market" to market
    ).generateParameters()

    val response = get(endpoint, generateHeader(accessToken), parameters)
    response.takeIf { it.statusCode == 403 }?.let { return null }
    response.responseCheck()

    return gson.fromJson<Paging<PlaylistTrack>>(response.text, Paging::class.java)
  }

  class Builder(private val accessToken: String, private val playlistId: String) {
    private var market: CountryCode? = null
    private var limit = 100
    private var offset = 0

    fun limit(limit: Int): Builder {
      this.limit = limit
      return this
    }

    fun offset(offset: Int): Builder {
      this.offset = offset
      return this
    }

    fun market(market: CountryCode): Builder {
      this.market = market
      return this
    }

    fun build(): GetPlaylistTracksQuery {
      limit.checkLimit(upper = 100)
      offset.checkOffset()

      return GetPlaylistTracksQuery(accessToken, playlistId, limit, offset, market?.alpha2)
    }
  }
}