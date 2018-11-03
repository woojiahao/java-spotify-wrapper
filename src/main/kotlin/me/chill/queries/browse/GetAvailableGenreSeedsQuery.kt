package me.chill.queries.browse

import me.chill.queries.AbstractQuery
import me.chill.utility.request.query
import me.chill.utility.request.readFromJsonArray

class GetAvailableGenreSeedsQuery private constructor(
	private val accessToken: String) : AbstractQuery<List<String>>("recommendations", "available-genre-seeds") {

	override fun execute() = gson.readFromJsonArray<String>("genres", query(queryEndpoint, accessToken))

	class Builder(private val accessToken: String) {
		fun build() = GetAvailableGenreSeedsQuery(accessToken)
	}
}