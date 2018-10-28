package me.chill.queries.artist

import me.chill.models.Artist

class GetSingleArtistQuery private constructor(
	private val id: String,
	private val accessToken: String) : SpotifyArtistQuery() {

	override fun execute(): Artist {
		val response = query(singleArtistEndpoint.format(id), accessToken)

		return gson.fromJson(response.text, Artist::class.java)
	}

	class Builder(private val id: String, private val accessToken: String) {
		fun build() = GetSingleArtistQuery(id, accessToken)
	}
}