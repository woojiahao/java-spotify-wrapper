package me.chill.queries.album

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Paging
import me.chill.models.Track
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLimit
import me.chill.utility.extensions.checkOffset
import me.chill.utility.extensions.read
import me.chill.utility.request.query

/**
 * Get Spotify catalog information about an album’s tracks
 */
class GetAlbumTracksQuery private constructor(
  private val accessToken: String,
  private val id: String,
  private val limit: Int,
  private val offset: Int,
  private val market: String?) : AbstractQuery<Paging<Track>>("albums", id, "tracks") {

  /**
   * @throws SpotifyQueryException if an error occurred with the operation
   * @return Tracks stored within a Paging object
   */
  override fun execute(): Paging<Track> {
    val parameters = mapOf(
      "limit" to limit,
      "offset" to offset,
      "market" to market
    )

    val response = query(queryEndpoint, accessToken, parameters)

    return gson.read(response.text)
  }

  class Builder(private val accessToken: String, private val id: String) {
    private var limit = 20
    private var offset = 0
    private var market: CountryCode? = null

    /**
     * @param limit The maximum number of tracks to return. Default: 20. Minimum: 1. Maximum: 50.
     */
    fun limit(limit: Int): Builder {
      this.limit = limit
      return this
    }

    /**
     * @param offset The index of the first track to return. Default: 0 (the first object).
     */
    fun offset(offset: Int): Builder {
      this.offset = offset
      return this
    }

    /**
     * @param market CountryCode constant
     */
    fun market(market: CountryCode): Builder {
      this.market = market
      return this
    }

    /**
     * @throws SpotifyQueryException **limit** is not between 1 and 50
     * @throws SpotifyQueryException **offset** is less than 0
     */
    fun build(): GetAlbumTracksQuery {
      limit.checkLimit()
      offset.checkOffset()

      return GetAlbumTracksQuery(accessToken, id, limit, offset, market?.alpha2)
    }
  }
}