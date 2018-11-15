package me.chill.queries.album

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Album
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmptyAndSizeLimit
import me.chill.utility.extensions.generateString
import me.chill.utility.extensions.readFromJsonArray
import me.chill.utility.extensions.splitAndAdd
import me.chill.utility.request.RequestMethod

/**
 * Get Spotify catalog information for multiple albums identified by their Spotify IDs
 */
class GetSeveralAlbumsQuery private constructor(
  private val accessToken: String,
  private val ids: Set<String>,
  private val market: String?) : AbstractQuery<List<Album?>>(accessToken, RequestMethod.Get, "albums") {

  /**
   * @throws SpotifyQueryException if an error with the operation occurs
   * @return List of possibly null Albums
   */
  override fun execute(): List<Album?> {
    val parameters = mapOf(
      "ids" to ids.generateString(),
      "market" to market
    )

    return gson.readFromJsonArray("albums", checkedQuery(parameters))
  }


  class Builder(private val accessToken: String) {
    private val albums = mutableSetOf<String>()
    private var market: String? = null

    /**
     * @param market CountryCode constant
     */
    fun market(market: CountryCode): Builder {
      this.market = market.alpha2
      return this
    }

    /**
     * Adds a single album ID to the albums list
     *
     * @param album Album ID
     */
    fun addAlbums(vararg albums: String): Builder {
      this.albums.splitAndAdd(albums)
      return this
    }

    /**
     * @throws SpotifyQueryException when the albums list is empty
     * @throws SpotifyQueryException when the albums list exceeds 20 ids
     */
    fun build(): GetSeveralAlbumsQuery {
      albums.checkEmptyAndSizeLimit("Albums")

      return GetSeveralAlbumsQuery(accessToken, albums, market)
    }
  }
}