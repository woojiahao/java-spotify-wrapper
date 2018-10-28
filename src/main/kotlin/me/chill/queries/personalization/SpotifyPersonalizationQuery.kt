package me.chill.queries.personalization

import me.chill.queries.Query
import me.chill.queries.checkLimit
import me.chill.queries.checkOffset

abstract class SpotifyPersonalizationQuery : Query() {
	protected val personalizationEndpoint = "${primaryEndpoint}v1/me/top/"
}