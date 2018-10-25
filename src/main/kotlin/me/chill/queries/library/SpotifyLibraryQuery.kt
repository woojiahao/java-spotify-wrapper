package me.chill.queries.library

import me.chill.queries.Query

abstract class SpotifyLibraryQuery : Query(){
	protected val libraryEndpoint = "${primaryEndpoint}v1/me/albums"
}
