package me.chill.queries.artist

import me.chill.models.Artist
import me.chill.queries.AbstractQuery
import me.chill.utility.request.query
import me.chill.utility.request.readFromJsonArray

class GetRelatedArtistsQuery private constructor(
	private val id: String,
	private val accessToken: String) : AbstractQuery("artists", id, "related-artists") {

	override fun execute() = gson.readFromJsonArray<Artist>("artists", query(queryEndpoint, accessToken))

	class Builder(private val id: String, private val accessToken: String) {
		fun build() = GetRelatedArtistsQuery(id, accessToken)
	}
}