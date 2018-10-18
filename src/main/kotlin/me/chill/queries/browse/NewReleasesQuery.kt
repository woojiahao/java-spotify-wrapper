package me.chill.queries.browse

import com.neovisionaries.i18n.CountryCode
import me.chill.models.NewReleases
import me.chill.queries.SpotifyQueryException

class NewReleasesQuery private constructor(
	private val accessToken: String,
	private val limit: Int,
	private val offset: Int,
	private val country: String?) : SpotifyBrowseQuery() {

	override fun execute(): NewReleases {
		val parameters = mapOf(
			"limit" to limit,
			"offset" to offset,
			"country" to country
		)

		val response = query("${browseEndpoint}new-releases", accessToken, parameters)

		return gson.fromJson(response.text, NewReleases::class.java)
	}

	class Builder(private val accessToken: String) {
		private var limit = 20
		private var offset = 0
		private var country: CountryCode? = null

		fun limit(limit: Int): Builder {
			if (limit < 1 || limit > 50) throw SpotifyQueryException("Limit must be greater than 0 and less than 51")
			this.limit = limit
			return this
		}

		fun offset(offset: Int): Builder {
			if (offset < 0) throw SpotifyQueryException("Offset must be greater than 0")
			this.offset = offset
			return this
		}

		fun country(country: CountryCode): Builder {
			this.country = country
			return this
		}

		fun build() = NewReleasesQuery(accessToken, limit, offset, country?.alpha2)
	}
}