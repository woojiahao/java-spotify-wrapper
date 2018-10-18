package me.chill.queries.album

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Paging
import me.chill.models.Track
import me.chill.queries.SpotifyQueryException

class AlbumTrackQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val limit: Int,
	private val offset: Int,
	private val market: String?) : SpotifyAlbumQuery() {

	override fun execute(): Paging<Track> {
		val parameters = mapOf(
			"limit" to limit,
			"offset" to offset,
			"market" to market
		)

		val response = query("$albumEndpoint$id/tracks", accessToken, parameters)

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