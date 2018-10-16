package me.chill.queries.artist

import me.chill.queries.Query

abstract class SpotifyArtistQuery : Query() {
	protected val artistEndpoint = "${primaryEndpoint}v1/artists/"
}