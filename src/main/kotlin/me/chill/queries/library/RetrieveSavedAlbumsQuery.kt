package me.chill.queries.library

import com.neovisionaries.i18n.CountryCode
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.Paging
import me.chill.models.SavedAlbum

class RetrieveSavedAlbumsQuery private constructor(
	private val accessToken: String,
	private val limit: Int,
	private val offset: Int,
	private val market: String?) : SpotifyLibraryQuery() {

	override fun execute(): Paging<SavedAlbum> {
		val parameters = mapOf(
			"limit" to limit,
			"offset" to offset,
			"market" to market
		)

		val response = query(libraryEndpoint, accessToken, parameters)

		return gson.fromJson<Paging<SavedAlbum>>(response.text, Paging::class.java)
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

		fun build(): RetrieveSavedAlbumsQuery {
			if (limit < 1 || limit > 50) throw SpotifyQueryException("Limit must be greater than 0 and less than 51")
			if (offset < 0) throw SpotifyQueryException("Offset must be greater than 0")

			return RetrieveSavedAlbumsQuery(accessToken, limit, offset, market?.alpha2)
		}
	}
}