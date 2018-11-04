package me.chill.queries.album

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Album
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmpty
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.generateString
import me.chill.utility.request.query
import me.chill.utility.request.readFromJsonArray

/**
 * Get Spotify catalog information for multiple albums identified by their Spotify IDs
 */
class GetSeveralAlbumsQuery private constructor(
  private val accessToken: String,
  private val ids: String,
  private val market: String?) : AbstractQuery<List<Album?>>("albums") {

  /**
   * @throws SpotifyQueryException if an error with the operation occurs
   * @return List of possibly null Albums
   */
  override fun execute(): List<Album?> {
    val parameters = mapOf(
      "ids" to ids,
      "market" to market
    )

    val response = query(queryEndpoint, accessToken, parameters)

    return gson.readFromJsonArray("albums", response)
  }


  class Builder(private val accessToken: String) {
    private val albums = mutableListOf<String>()
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
    fun addAlbum(album: String): Builder {
      albums.add(album)
      return this
    }

    /**
     * Clears any pre-existing album ids and re-populates the albums list with the input
     *
     * @param albums List of album IDs to be supplied
     */
    fun setAlbums(albums: List<String>): Builder {
      this.albums.clear()
      this.albums.addAll(albums)
      return this
    }

    /**
     * @throws SpotifyQueryException when the albums list is empty
     * @throws SpotifyQueryException when the albums list exceeds 20 ids
     */
    fun build(): GetSeveralAlbumsQuery {
      albums.checkEmpty("Albums")
      albums.checkListSizeLimit("Albums")

      return GetSeveralAlbumsQuery(accessToken, albums.generateString(), market)
    }
  }
}