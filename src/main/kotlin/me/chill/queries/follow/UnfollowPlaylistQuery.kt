package me.chill.queries.follow

import khttp.delete
import me.chill.queries.AbstractQuery
import me.chill.utility.request.Header
import me.chill.utility.request.generateHeader
import me.chill.utility.request.responseCheck

class UnfollowPlaylistQuery private constructor(
	private val id: String,
	private val accessToken: String) : AbstractQuery<Boolean>("playlists", id, "followers") {

	override fun execute(): Boolean {
		val response = delete(queryEndpoint, generateHeader(accessToken))
		response.responseCheck()

		return response.statusCode == 200
	}

	class Builder(private val playlistId: String, private val accessToken: String) {
		fun build() = UnfollowPlaylistQuery(playlistId, accessToken)
	}
}