package me.chill.queries.artist

import me.chill.queries.Query

abstract class SpotifyArtistQuery : Query() {
	protected val artistEndpoint = "${primaryEndpoint}v1/artists/"
	protected val singleArtistEndpoint = "$artistEndpoint%s"
	protected val artistAlbumsEndpoint = "$singleArtistEndpoint/albums"
	protected val artistTopTracksEndpoint = "$singleArtistEndpoint/top-tracks"
	protected val relatedArtistEndpoint = "$singleArtistEndpoint/related-artists"
}