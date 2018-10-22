package me.chill.queries.follow

import me.chill.queries.Query

abstract class SpotifyFollowQuery : Query() {
	protected val followEndpoint = "${primaryEndpoint}v1/me/following/"
}