package me.chill.queries.personalization

import me.chill.models.Artist
import me.chill.models.Paging
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLimit
import me.chill.utility.extensions.checkOffset
import me.chill.utility.request.query

class GetUserTopArtistsQuery private constructor(
	private val accessToken: String,
	private val limit: Int,
	private val offset: Int,
	private val timeRange: String) : AbstractQuery("me", "top", "artists") {

	override fun execute(): Paging<Artist> {
		val parameters = mapOf(
			"limit" to limit,
			"offset" to offset,
			"time_range" to timeRange
		)

		val response = query(queryEndpoint, accessToken, parameters)

		return gson.fromJson<Paging<Artist>>(response.text, Paging::class.java)
	}

	class Builder(private val accessToken: String) {
		private var limit = 20
		private var offset = 0
		private var timeRange = TimeRange.Medium

		fun limit(limit: Int): Builder {
			this.limit = limit
			return this
		}

		fun offset(offset: Int): Builder {
			this.offset = offset
			return this
		}

		fun timeRange(timeRange: TimeRange): Builder {
			this.timeRange = timeRange
			return this
		}

		fun build(): GetUserTopArtistsQuery {
			limit.checkLimit()
			offset.checkOffset()

			return GetUserTopArtistsQuery(accessToken, limit, offset, timeRange.getValue())
		}
	}
}
