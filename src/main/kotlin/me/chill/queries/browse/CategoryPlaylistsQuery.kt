package me.chill.queries.browse

import com.google.gson.JsonObject
import com.neovisionaries.i18n.CountryCode
import khttp.get
import me.chill.models.Paging
import me.chill.models.Playlist
import me.chill.queries.SpotifyQueryException
import me.chill.utility.responseCheck

class CategoryPlaylistsQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val limit: Int,
	private val offset: Int,
	private val country: String?) : SpotifyBrowseQuery() {

	override fun execute(): Paging<Playlist> {
		val parameters = mutableMapOf(
			"limit" to limit.toString(),
			"offset" to offset.toString()
		)

		country?.let { parameters["country"] = it }

		val response = get("${browseEndpoint}categories/$id/playlists", generateHeaders(accessToken), parameters)

		response.responseCheck()

		return gson.fromJson<Paging<Playlist>>(gson.fromJson(response.text, JsonObject::class.java)["playlists"], Paging::class.java)
	}

	class Builder(private val id: String, private val accessToken: String) {
		private var country: CountryCode? = null
		private var limit = 20
		private var offset = 0

		fun country(country: CountryCode): Builder {
			this.country = country
			return this
		}

		fun limit(limit: Int): Builder {
			if (limit < 1 || limit > 50) throw SpotifyQueryException("Limit must be greater than 0 and less than 51")
			this.limit = limit
			return this
		}

		fun offset(offset: Int): Builder {
			if (offset < 0) throw SpotifyQueryException("Offset cannot be less than 0")
			this.offset = offset
			return this
		}

		fun build() = CategoryPlaylistsQuery(id, accessToken, limit, offset, country?.alpha2)
	}
}