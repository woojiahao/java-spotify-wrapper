package me.chill.queries.browse

import com.google.gson.JsonObject
import me.chill.queries.readFromJsonArray

class GetAvailableGenreSeedsQuery private constructor(
	private val accessToken: String) : SpotifyBrowseQuery() {

	override fun execute(): List<String> {
		val response = query(genreSeedsEndpoint, accessToken)

		return gson.readFromJsonArray("genres", response)
	}

	class Builder(private val accessToken: String) {
		fun build() = GetAvailableGenreSeedsQuery(accessToken)
	}
}