package me.chill.queries.artist

import khttp.get
import me.chill.models.Artist
import me.chill.utility.responseCheck

class SingleArtistQuery private constructor(
	private val id: String,
	private val accessToken: String) : SpotifyArtistQuery() {

	override fun execute(): Artist {
		val response = get("$artistEndpoint$id", generateHeaders(accessToken))

		response.responseCheck()

		return gson.fromJson(response.text, Artist::class.java)
	}

	class Builder(private val id: String, private val accessToken: String) {
		fun build(): SingleArtistQuery = SingleArtistQuery(id, accessToken)
	}
}