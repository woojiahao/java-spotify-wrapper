package me.chill.queries.browse

import com.neovisionaries.i18n.CountryCode
import khttp.get
import me.chill.models.Paging
import me.chill.models.Playlist
import me.chill.queries.SpotifyQueryException
import me.chill.utility.responseCheck
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

data class FeaturedPlaylists(
	val message: String,
	val playlists: Paging<Playlist>
)

class FeaturedPlaylistsQuery private constructor(
	private val accessToken: String,
	private val limit: Int,
	private val offset: Int,
	private val locale: String?,
	private val country: String?,
	private val timestamp: String?) : SpotifyBrowseQuery() {

	override fun execute(): FeaturedPlaylists {
		val parameters = mapOf(
			"limit" to limit,
			"offset" to offset,
			"locale" to locale,
			"country" to country,
			"timestamp" to timestamp
		).generateParameters()
		
		val response = get("${browseEndpoint}featured-playlists", generateHeaders(accessToken), parameters)
		response.responseCheck()

		return gson.fromJson(response.text, FeaturedPlaylists::class.java)
	}

	class Builder(private val accessToken: String) {
		private var limit = 20
		private var offset = 0
		private var country: CountryCode? = null
		private var locale: String? = null
		private var timestamp: String? = null

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

		fun locale(language: Locale, countryCode: CountryCode): Builder {
			this.locale = "${language.language}_${countryCode.alpha2}"
			return this
		}

		fun timestamp(year: Int, month: Int, date: Int, hour: Int, minute: Int, second: Int): Builder {
			val isoString = "$year-${padTimeUnit(month)}-${padTimeUnit(date)}T${padTimeUnit(hour)}:${padTimeUnit(minute)}:${padTimeUnit(second)}"
			val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

			try {
				formatter.parse(isoString)
			} catch (e: DateTimeParseException) {
				throw SpotifyQueryException("Invalid input for a timestamp")
			}

			this.timestamp = isoString
			return this
		}

		fun build() = FeaturedPlaylistsQuery(accessToken, limit, offset, locale, country?.alpha2, timestamp)

		private fun padTimeUnit(timeUnit: Int) = timeUnit.toString().padStart(2, '0')
	}
}