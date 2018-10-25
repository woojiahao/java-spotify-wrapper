package me.chill.queries.browse

import com.google.gson.JsonObject
import com.neovisionaries.i18n.CountryCode
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.Paging
import me.chill.models.Playlist
import me.chill.queries.checkLimit
import me.chill.queries.checkLower
import me.chill.queries.checkOffset
import me.chill.queries.checkRange

class CategoryPlaylistsQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val limit: Int,
	private val offset: Int,
	private val country: String?) : SpotifyBrowseQuery() {

	override fun execute(): Paging<Playlist> {
		val parameters = mutableMapOf(
			"limit" to limit,
			"offset" to offset,
			"country" to country
		)

		val response = query("${browseEndpoint}categories/$id/playlists", accessToken, parameters)

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
			this.limit = limit
			return this
		}

		fun offset(offset: Int): Builder {
			this.offset = offset
			return this
		}

		fun build(): CategoryPlaylistsQuery {
			limit.checkLimit()
			offset.checkOffset()

			return CategoryPlaylistsQuery(id, accessToken, limit, offset, country?.alpha2)
		}
	}
}