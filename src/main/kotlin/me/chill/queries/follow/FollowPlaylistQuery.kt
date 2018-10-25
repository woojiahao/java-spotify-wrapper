package me.chill.queries.follow

class FollowPlaylistQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val public: Boolean) : SpotifyFollowQuery() {

	override fun execute(): Boolean {
		val body = mapOf(
			"public" to public
		).generateParameters()

		val headers = mapOf(
			"Authorization" to "Bearer $accessToken",
			"Content-Type" to "application/json"
		)

		val response = khttp.put("https://api.spotify.com/v1/playlists/$id/followers", headers, data = body)
		response.responseCheck()

		return response.statusCode == 200
	}

	class Builder(private val id: String, private val accessToken: String) {
		private var public = true

		fun public(public: Boolean): Builder {
			this.public = public
			return this
		}

		fun build() = FollowPlaylistQuery(id, accessToken, public)
	}
}