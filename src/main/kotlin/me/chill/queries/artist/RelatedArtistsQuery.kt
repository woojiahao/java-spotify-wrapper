package me.chill.queries.artist

import com.google.gson.JsonObject
import khttp.get
import me.chill.models.Artist
import me.chill.utility.responseCheck

class RelatedArtistsQuery private constructor(
	private val id: String,
	private val accessToken: String) : SpotifyArtistQuery() {

	override fun execute(): List<Artist> {
		val response = get("$artistEndpoint$id/related-artists", generateHeaders(accessToken))

		response.responseCheck()

		return gson
			.fromJson(response.text, JsonObject::class.java)
			.getAsJsonArray("artists")
			.map { gson.fromJson(it, Artist::class.java) }
	}

	class Builder(private val id: String, private val accessToken: String) {
		fun build() = RelatedArtistsQuery(id, accessToken)
	}
}