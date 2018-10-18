package me.chill.queries.artist

import com.google.gson.JsonObject
import com.neovisionaries.i18n.CountryCode
import khttp.get
import me.chill.models.Track
import me.chill.queries.SpotifyQueryException
import me.chill.utility.responseCheck

class ArtistTopTrackQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val market: String) : SpotifyArtistQuery() {

	override fun execute(): List<Track> {
		val parameters = mapOf(
			"market" to market
		)

		val response = query("$artistEndpoint$id/top-tracks", accessToken, parameters)



		return gson
			.fromJson(response.text, JsonObject::class.java)
			.getAsJsonArray("tracks")
			.map { gson.fromJson(it, Track::class.java) }
	}

	class Builder(private val id: String, private val accessToken: String) {
		private var market: CountryCode? = null

		fun market(market: CountryCode): Builder {
			this.market = market
			return this
		}

		fun build(): ArtistTopTrackQuery {
			market
				?: throw SpotifyQueryException("You must specify the market in order to retrieve the artist's top tracks")
			return ArtistTopTrackQuery(id, accessToken, market!!.alpha2)
		}
	}
}