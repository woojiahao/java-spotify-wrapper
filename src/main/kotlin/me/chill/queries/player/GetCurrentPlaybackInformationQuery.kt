package me.chill.queries.player

import com.neovisionaries.i18n.CountryCode
import me.chill.models.CurrentlyPlayingContext

class GetCurrentPlaybackInformationQuery private constructor(
	private val accessToken: String,
	private val market: String?) : SpotifyPlayerQuery() {

	override fun execute(): CurrentlyPlayingContext? {
		val parameters = mapOf("market" to market)

		val response = query(playerEndpoint, accessToken, parameters)

		if (response.text.isEmpty()) return null

		return gson.fromJson(response.text, CurrentlyPlayingContext::class.java)
	}

	class Builder(private val accessToken: String) {
		private var market: CountryCode? = null

		fun market(market: CountryCode): Builder {
			this.market = market
			return this
		}

		fun build() = GetCurrentPlaybackInformationQuery(accessToken, market?.alpha2)
	}
}