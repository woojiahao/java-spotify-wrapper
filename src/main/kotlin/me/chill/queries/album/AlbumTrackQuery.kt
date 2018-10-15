package me.chill.queries.album

import com.neovisionaries.i18n.CountryCode
import khttp.get
import me.chill.models.Paging
import me.chill.models.Track
import me.chill.queries.SpotifyQueryException
import me.chill.utility.responseCheck

class AlbumTrackQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val limit: Int,
	private val offset: Int,
	private val market: String?) : SpotifyAlbumQuery() {

	override fun execute(): Paging<Track> {
		val parameters = mutableMapOf(
			"limit" to limit.toString(),
			"offset" to offset.toString()
		)

		market?.let { parameters["market"] = it }

		val response = get("$primaryEndpoint$id/tracks", generateHeaders(accessToken), parameters)

		response.responseCheck()

		return gson.fromJson<Paging<Track>>(response.text, Paging::class.java)
	}

	class Builder(private val id: String, private val accessToken: String) {
		private var limit = 20
		private var offset = 0
		private var market: CountryCode? = null

		fun limit(limit: Int): Builder {
			if (limit < 1 || limit > 50) throw SpotifyQueryException("Limit cannot be less than 1 or more than 50")
			this.limit = limit
			return this
		}

		// TODO: Check if the offset is valid, maybe?
		fun offset(offset: Int): Builder {
			if (offset < 0) throw SpotifyQueryException("Offset cannot be less than 0")
			this.offset = offset
			return this
		}

		fun market(market: CountryCode): Builder {
			this.market = market
			return this
		}

		fun build() = AlbumTrackQuery(id, accessToken, limit, offset, market?.alpha2)
	}
}