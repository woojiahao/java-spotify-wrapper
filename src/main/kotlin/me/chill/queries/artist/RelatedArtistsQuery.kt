package me.chill.queries.artist

import com.google.gson.JsonObject
import me.chill.models.Artist

class RelatedArtistsQuery private constructor(
	private val id: String,
	private val accessToken: String) : SpotifyArtistQuery() {

	override fun execute(): List<Artist> {
		val response = query("$artistEndpoint$id/related-artists", accessToken)

		return gson
			.fromJson(response.text, JsonObject::class.java)
			.getAsJsonArray("artists")
			.map { gson.fromJson(it, Artist::class.java) }
	}

	class Builder(private val id: String, private val accessToken: String) {
		fun build() = RelatedArtistsQuery(id, accessToken)
	}
}