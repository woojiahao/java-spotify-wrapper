package me.chill.queries.follow

class FollowPlaylistQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val public: Boolean) : SpotifyFollowQuery() {

	override fun execute(): Boolean {
		val body = mapOf(
			"public" to public
		).generateParameters()

		val response = khttp.put(followPlaylistEndpoint.format(id), generateModificationHeaders(accessToken), data = body)
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