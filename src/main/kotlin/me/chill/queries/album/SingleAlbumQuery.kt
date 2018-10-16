package me.chill.queries.album

import com.neovisionaries.i18n.CountryCode
import khttp.get
import me.chill.models.Album
import me.chill.utility.responseCheck

class SingleAlbumQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val market: String?) : SpotifyAlbumQuery() {

	override fun execute(): Album {
		val parameters = market?.let { mapOf("market" to it) } ?: emptyMap()

		val response = get("$albumEndpoint$id", generateHeaders(accessToken), parameters)
		response.responseCheck()

		return gson.fromJson(response.text, Album::class.java)
	}

	class Builder(private val id: String, private val accessToken: String) {
		private var market: CountryCode? = null

		fun market(market: CountryCode): Builder {
			this.market = market
			return this
		}

		fun build() = SingleAlbumQuery(id, accessToken, market?.alpha2)
	}
}