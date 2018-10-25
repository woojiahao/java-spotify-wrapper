package me.chill.queries.browse

import com.google.gson.JsonObject

class AvailableGenreSeedsQuery private constructor(
	private val accessToken: String) : SpotifyBrowseQuery() {

	override fun execute(): List<String> {
		val response = query("https://api.spotify.com/v1/recommendations/available-genre-seeds", accessToken)

		return gson
			.fromJson(response.text, JsonObject::class.java)
			.getAsJsonArray("genres")
			.map { it.asString }
	}

	class Builder(private val accessToken: String) {
		fun build() = AvailableGenreSeedsQuery(accessToken)
	}
}