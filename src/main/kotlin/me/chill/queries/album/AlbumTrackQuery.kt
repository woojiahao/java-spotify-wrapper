package me.chill.queries.album

import com.neovisionaries.i18n.CountryCode
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.Paging
import me.chill.models.Track
import me.chill.queries.checkLower
import me.chill.queries.checkRange

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
			this.limit = limit
			return this
		}

		fun offset(offset: Int): Builder {
			this.offset = offset
			return this
		}

		fun market(market: CountryCode): Builder {
			this.market = market
			return this
		}

		fun build(): AlbumTrackQuery {
			limit.checkLimit()
			offset.checkOffset()

			return AlbumTrackQuery(id, accessToken, limit, offset, market?.alpha2)
		}
	}
}