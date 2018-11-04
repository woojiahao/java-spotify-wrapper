package me.chill.queries.album

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Album
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.query

/**
 * Get Spotify catalog information for a single album
 */
class GetSingleAlbumQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val market: String?) : AbstractQuery<Album>("albums", id) {

	/**
	 * @throws SpotifyQueryException if an error occurred with the operation
	 * @return Single album object
	 */
	override fun execute(): Album {
		val parameters = mapOf("market" to market)

		val response = query(queryEndpoint, accessToken, parameters)

		return gson.read(response.text)
	}

	class Builder(private val id: String, private val accessToken: String) {
		private var market: CountryCode? = null

		/**
		 * @param market CountryCode constant
		 */
		fun market(market: CountryCode): Builder {
			this.market = market
			return this
		}

		fun build() = GetSingleAlbumQuery(id, accessToken, market?.alpha2)
	}
}