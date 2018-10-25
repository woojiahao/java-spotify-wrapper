package me.chill.queries.follow

import khttp.delete
import me.chill.utility.responseCheck

class UnfollowPlaylistQuery private constructor(
	private val playlistId: String,
	private val accessToken: String): SpotifyFollowQuery() {

	override fun execute(): Any {
		val response = delete("https://api.spotify.com/v1/playlists/$playlistId/followers", generateHeaders(accessToken))
		response.responseCheck()

		return response.statusCode == 200
	}

	class Builder(private val playlistId: String, private val accessToken: String) {
		fun build() = UnfollowPlaylistQuery(playlistId, accessToken)
	}
}