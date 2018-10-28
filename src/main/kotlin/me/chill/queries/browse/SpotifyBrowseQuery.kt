package me.chill.queries.browse

import me.chill.queries.Query

abstract class SpotifyBrowseQuery : Query() {
	protected val browseEndpoint = "${primaryEndpoint}v1/browse/"
	protected val categoriesEndpoint = "${browseEndpoint}categories/"
	protected val singleCategoryEndpoint = "$categoriesEndpoint%s"
	protected val categoryPlaylistsEndpoint = "$singleCategoryEndpoint/playlists"
	protected val featuredPlaylistsEndpoint = "${browseEndpoint}featured-playlists"
	protected val newReleaseEndpoint = "${browseEndpoint}new-releases"
	protected val recommendationSeedEndpoint = "${primaryEndpoint}v1/recommendations/"
	protected val genreSeedsEndpoint = "${recommendationSeedEndpoint}available-genre-seeds"
}