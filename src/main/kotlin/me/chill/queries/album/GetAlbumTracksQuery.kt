package me.chill.queries.album

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Paging
import me.chill.models.Track
import me.chill.queries.checkLimit
import me.chill.queries.checkOffset

class GetAlbumTracksQuery private constructor(
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

		val response = query(albumTrackEndpoint.format(id), accessToken, parameters)

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

		fun build(): GetAlbumTracksQuery {
			limit.checkLimit()
			offset.checkOffset()

			return GetAlbumTracksQuery(id, accessToken, limit, offset, market?.alpha2)
		}
	}
}