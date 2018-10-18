package me.chill.queries.artist

import com.google.gson.JsonObject
import me.chill.models.Artist
import me.chill.queries.SpotifyQueryException
import me.chill.utility.responseCheck

class ManyArtistQuery private constructor(
	private val accessToken: String,
	private val ids: String) : SpotifyArtistQuery() {

	override fun execute(): List<Artist> {
		val parameters = mapOf("ids" to ids)

		val response = query(artistEndpoint, accessToken, parameters)

		response.responseCheck()

		return gson
			.fromJson(response.text, JsonObject::class.java)
			.getAsJsonArray("artists")
			.map { gson.fromJson(it, Artist::class.java) }
	}

	class Builder(private val accessToken: String) {
		private val ids = mutableListOf<String>()

		fun addId(id: String): Builder {
			ids.add(id)
			return this
		}

		fun setIds(ids: List<String>): Builder {
			this.ids.clear()
			this.ids.addAll(ids)
			return this
		}

		fun build(): ManyArtistQuery {
			if (ids.isEmpty()) throw SpotifyQueryException("ID list must consist of at least 1 ID")
			if (ids.size > 50) throw SpotifyQueryException("ID list cannot contain more than 50 IDs")
			return ManyArtistQuery(accessToken, ids.joinToString(","))
		}
	}
}