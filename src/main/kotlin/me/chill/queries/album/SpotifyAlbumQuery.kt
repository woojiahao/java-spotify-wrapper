package me.chill.queries.album

import me.chill.queries.Query

abstract class SpotifyAlbumQuery : Query() {
	protected val albumEndpoint = "${primaryEndpoint}v1/albums/"
}