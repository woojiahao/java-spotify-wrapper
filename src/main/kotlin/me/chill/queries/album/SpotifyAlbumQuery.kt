package me.chill.queries.album

import me.chill.queries.Query

abstract class SpotifyAlbumQuery : Query() {
	protected val primaryEndpoint = "https://api.spotify.com/v1/albums/"
}