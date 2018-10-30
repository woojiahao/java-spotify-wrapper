package me.chill.queries.player

import me.chill.exceptions.SpotifyQueryException
import me.chill.models.CursorBasedPaging
import me.chill.models.PlayHistory
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLimit
import me.chill.utility.extensions.checkLower
import me.chill.utility.request.query

class GetRecentlyPlayedTracksQuery private constructor(
	private val accessToken: String,
	private val limit: Int,
	private val after: Int?,
	private val before: Int?) : AbstractQuery("me", "player", "recently-played") {

	override fun execute(): CursorBasedPaging<PlayHistory>? {
		val parameters = mapOf(
			"limit" to limit,
			"after" to after,
			"before" to before
		)

		val response = query(queryEndpoint, accessToken, parameters)

		response.statusCode.takeIf { it == 204 }?.let { return null }

		return gson.fromJson<CursorBasedPaging<PlayHistory>>(response.text, CursorBasedPaging::class.java)
	}

	class Builder(private val accessToken: String) {
		private var limit = 20
		private var after: Int? = null
		private var before: Int? = null

		fun limit(limit: Int): Builder {
			this.limit = limit
			return this
		}

		fun after(after: Int): Builder {
			this.after = after
			return this
		}

		fun before(before: Int): Builder {
			this.before = before
			return this
		}

		fun build(): GetRecentlyPlayedTracksQuery {
			limit.checkLimit()
			after?.checkLower("After")
			before?.checkLower("Before")

			after?.let {
				before?.let { _ -> throw SpotifyQueryException("Before cannot be set when after is set") }
			}

			before?.let {
				after?.let { _ -> throw SpotifyQueryException("After cannot be set when before is set") }
			}

			return GetRecentlyPlayedTracksQuery(accessToken, limit, after, before)
		}
	}
}