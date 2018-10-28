package me.chill.queries.artist

import com.google.gson.JsonObject
import me.chill.models.Artist
import me.chill.queries.checkEmpty
import me.chill.queries.checkListSizeLimit
import me.chill.queries.generateString

class GetSeveralArtistsQuery private constructor(
	private val accessToken: String,
	private val ids: String) : SpotifyArtistQuery() {

	override fun execute(): List<Artist> {
		val parameters = mapOf("ids" to ids)

		val response = query(artistEndpoint, accessToken, parameters)

		return gson
			.fromJson(response.text, JsonObject::class.java)
			.getAsJsonArray("artists")
			.map { gson.fromJson(it, Artist::class.java) }
	}

	class Builder(private val accessToken: String) {
		private val artists = mutableListOf<String>()

		fun addId(artist: String): Builder {
			artists.add(artist)
			return this
		}

		fun setIds(artists: List<String>): Builder {
			this.artists.clear()
			this.artists.addAll(artists)
			return this
		}

		fun build(): GetSeveralArtistsQuery {
			artists.checkEmpty("Artists")
			artists.checkListSizeLimit("Artists", 50)

			return GetSeveralArtistsQuery(accessToken, artists.generateString())
		}
	}
}