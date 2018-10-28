package me.chill.queries.personalization

import me.chill.models.Artist
import me.chill.models.Paging
import me.chill.queries.checkLimit
import me.chill.queries.checkOffset

class UserTopArtistsQuery private constructor(
	private val accessToken: String,
	private val limit: Int,
	private val offset: Int,
	private val timeRange: String) : SpotifyPersonalizationQuery() {

	override fun execute(): Paging<Artist> {
		val parameters = mapOf(
			"limit" to limit,
			"offset" to offset,
			"time_range" to timeRange
		)

		val response = query(topArtistsEndpoint, accessToken, parameters)

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

		fun build(): UserTopArtistsQuery {
			limit.checkLimit()
			offset.checkOffset()

			return UserTopArtistsQuery(accessToken, limit, offset, timeRange.getValue())
		}
	}
}
