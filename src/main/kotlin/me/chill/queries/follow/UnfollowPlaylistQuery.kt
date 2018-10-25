package me.chill.queries.follow

import khttp.delete

class UnfollowPlaylistQuery private constructor(
	private val playlistId: String,
	private val accessToken: String) : SpotifyFollowQuery() {

	override fun execute(): Boolean {
		val response = delete("https://api.spotify.com/v1/playlists/$playlistId/followers", generateHeaders(accessToken))
		response.responseCheck()

		return response.statusCode == 200
	}

	class Builder(private val playlistId: String, private val accessToken: String) {
		fun build() = UnfollowPlaylistQuery(playlistId, accessToken)
	}
}