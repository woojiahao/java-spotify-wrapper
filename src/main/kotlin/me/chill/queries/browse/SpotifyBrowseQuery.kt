package me.chill.queries.browse

import me.chill.queries.Query


abstract class SpotifyBrowseQuery : Query() {
	protected val browseEndpoint = "${primaryEndpoint}v1/browse/"
}