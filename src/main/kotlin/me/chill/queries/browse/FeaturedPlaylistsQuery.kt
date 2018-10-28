package me.chill.queries.browse

import com.neovisionaries.i18n.CountryCode
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.FeaturedPlaylists
import me.chill.queries.checkLimit
import me.chill.queries.checkOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

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
		)

		val response = query(featuredPlaylistsEndpoint, accessToken, parameters)

		return gson.fromJson(response.text, FeaturedPlaylists::class.java)
	}

	class Builder(private val accessToken: String) {
		private var limit = 20
		private var offset = 0
		private var country: CountryCode? = null
		private var locale: String? = null
		private var timestamp: String? = null

		fun limit(limit: Int): Builder {
			this.limit = limit
			return this
		}

		fun offset(offset: Int): Builder {
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

		fun build(): FeaturedPlaylistsQuery {
			limit.checkLimit()
			offset.checkOffset()

			return FeaturedPlaylistsQuery(accessToken, limit, offset, locale, country?.alpha2, timestamp)
		}

		private fun padTimeUnit(timeUnit: Int) = timeUnit.toString().padStart(2, '0')
	}
}