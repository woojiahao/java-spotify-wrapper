package me.chill.queries.personalization

import me.chill.queries.Query

abstract class SpotifyPersonalizationQuery : Query() {
	protected val personalizationEndpoint = "${primaryEndpoint}v1/me/top/"
	protected val topTracksEndpoint = "${personalizationEndpoint}tracks"
	protected val topArtistsEndpoint = "${personalizationEndpoint}artists"
}