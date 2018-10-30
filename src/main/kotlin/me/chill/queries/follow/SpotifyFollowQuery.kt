package me.chill.queries.follow

import me.chill.queries.Query

abstract class SpotifyFollowQuery : Query() {
	protected val followEndpoint = "${primaryEndpoint}v1/me/following/"
	private val playlistEndpoint = "${primaryEndpoint}v1/playlists/%s/"
	protected val isFollowingArtistOrUserEndpoint = "${followEndpoint}contains"
	protected val isFollowingPlaylistEndpoint = "${playlistEndpoint}followers/contains"
	protected val followPlaylistEndpoint = "${playlistEndpoint}followers"
}