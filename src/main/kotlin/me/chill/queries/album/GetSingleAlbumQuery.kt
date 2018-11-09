package me.chill.queries.album

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Album
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.query

/**
 * Gets Spotify catalog information for a single album.
 * @see <a href="https://developer.spotify.com/documentation/web-api/reference/albums/get-album/">https://developer.spotify.com/documentation/web-api/reference/albums/get-album/</a>
 */
class GetSingleAlbumQuery private constructor(
  private val accessToken: String,
  private val albumId: String,
  private val market: String?) : AbstractQuery<Album>("albums", albumId) {

  /**
   * Gets information about a single album.
   * @throws SpotifyQueryException if the status code of the response is greater than 400
   * @return Album queried
   */
  override fun execute(): Album {
    val parameters = mapOf("market" to market)

    val response = query(endpoint, accessToken, parameters)

    return gson.read(response.text)
  }

  class Builder(private val accessToken: String, private val albumId: String) {
    private var market: CountryCode? = null

    /**
     * Sets the **market** parameter.
     * @param market CountryCode constant
     */
    fun market(market: CountryCode): Builder {
      this.market = market
      return this
    }

    fun build() = GetSingleAlbumQuery(accessToken, albumId, market?.alpha2)
  }
}