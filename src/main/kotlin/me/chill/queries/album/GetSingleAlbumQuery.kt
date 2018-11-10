package me.chill.queries.album

import com.neovisionaries.i18n.CountryCode
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.Album
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.query

/**
 * Get Spotify catalog information for a single album.
 *
 * This endpoint can be access anonymously.
 *
 * **Query Parameters:**
 * - **market** (Optional) - CountryCode of the market you wish to access the album from.
 *
 * @see <a href="https://developer.spotify.com/documentation/web-api/reference/albums/get-album/">Endpoint Documentation</a>
 */
class GetSingleAlbumQuery private constructor(
  private val accessToken: String,
  private val albumId: String,
  private val market: String?) : AbstractQuery<Album>("albums", albumId) {

  /**
   * Gets information about a single album.
   * @throws [SpotifyQueryException] if the status code of the response is greater than or equal to 400.
   * @return [Album] queried.
   */
  override fun execute() = gson.read<Album>(query(endpoint, accessToken, mapOf("market" to market)).text)

  /**
   * Builder for [GetSingleAlbumQuery].
   *
   * @param accessToken Access token to be used to access the API.
   * @param albumId ID of album to retrieve.
   */
  class Builder(private val accessToken: String, private val albumId: String) {
    private var market: CountryCode? = null

    /**
     * Sets the **market** parameter.
     * @param market [CountryCode] constant
     */
    fun market(market: CountryCode): Builder {
      this.market = market
      return this
    }

    /**
     * Builds a [GetSingleAlbumQuery].
     *
     * @return [GetSingleAlbumQuery] for to execute with the parameters set by the builder.
     */
    fun build() = GetSingleAlbumQuery(accessToken, albumId, market?.alpha2)
  }
}