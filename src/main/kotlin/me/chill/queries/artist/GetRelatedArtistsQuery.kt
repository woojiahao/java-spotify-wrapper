package me.chill.queries.artist

import com.google.gson.JsonObject
import me.chill.models.Artist

class GetRelatedArtistsQuery private constructor(
	private val id: String,
	private val accessToken: String) : SpotifyArtistQuery() {

	override fun execute(): List<Artist> {
		val response = query(relatedArtistEndpoint.format(id), accessToken)

		return gson
			.fromJson(response.text, JsonObject::class.java)
			.getAsJsonArray("artists")
			.map { gson.fromJson(it, Artist::class.java) }
	}

	class Builder(private val id: String, private val accessToken: String) {
		fun build() = GetRelatedArtistsQuery(id, accessToken)
	}
}