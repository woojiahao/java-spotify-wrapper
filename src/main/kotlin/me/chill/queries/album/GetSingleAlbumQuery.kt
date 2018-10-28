package me.chill.queries.album

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Album

class GetSingleAlbumQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val market: String?) : SpotifyAlbumQuery() {

	override fun execute(): Album {
		val parameters = mapOf("market" to market)

		val response = query(singleAlbumEndpoint.format(id), accessToken, parameters)

		return gson.fromJson(response.text, Album::class.java)
	}

	class Builder(private val id: String, private val accessToken: String) {
		private var market: CountryCode? = null

		fun market(market: CountryCode): Builder {
			this.market = market
			return this
		}

		fun build() = GetSingleAlbumQuery(id, accessToken, market?.alpha2)
	}
}