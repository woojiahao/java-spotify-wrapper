package me.chill.queries.album

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Paging
import me.chill.models.Track
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLimit
import me.chill.utility.extensions.checkOffset
import me.chill.utility.request.query
import kotlin.concurrent.thread

class GetAlbumTracksQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val limit: Int,
	private val offset: Int,
	private val market: String?) : AbstractQuery("albums", id, "tracks") {

	override fun execute(): Paging<Track> {
		val parameters = mapOf(
			"limit" to limit,
			"offset" to offset,
			"market" to market
		)

		val response = query(queryEndpoint, accessToken, parameters)

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