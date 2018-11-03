package me.chill.queries.artist

import me.chill.models.Artist
import me.chill.queries.AbstractQuery
import me.chill.utility.request.query

class GetSingleArtistQuery private constructor(
	private val id: String,
	private val accessToken: String) : AbstractQuery<Artist>("artists", id) {

	override fun execute(): Artist = gson.fromJson(query(queryEndpoint, accessToken).text, Artist::class.java)

	class Builder(private val id: String, private val accessToken: String) {
		fun build() = GetSingleArtistQuery(id, accessToken)
	}
}