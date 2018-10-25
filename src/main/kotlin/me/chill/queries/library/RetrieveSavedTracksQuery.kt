package me.chill.queries.library

import com.neovisionaries.i18n.CountryCode
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.Paging
import me.chill.models.SavedTrack

class RetrieveSavedTracksQuery private constructor(
	private val accessToken: String,
	private val limit: Int,
	private val offset: Int,
	private val market: String?) : SpotifyLibraryQuery() {

	override fun execute(): Paging<SavedTrack> {
		val parameters = mapOf(
			"limit" to limit,
			"offset" to offset,
			"market" to market
		)

		val response = query("https://api.spotify.com/v1/me/tracks", accessToken, parameters)

		return gson.fromJson<Paging<SavedTrack>>(response.text, Paging::class.java)
	}

	class Builder(private val accessToken: String) {
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

		fun build(): RetrieveSavedTracksQuery {
			if (limit < 1 || limit > 50) throw SpotifyQueryException("Limit must be greater than 0 and less than 51")
			if (offset < 0) throw SpotifyQueryException("Offset must be greater than 0")

			return RetrieveSavedTracksQuery(accessToken, limit, offset, market?.alpha2)
		}
	}
}