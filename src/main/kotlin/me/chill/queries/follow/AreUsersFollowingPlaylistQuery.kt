package me.chill.queries.follow

import me.chill.queries.checkEmpty
import me.chill.queries.checkListSizeLimit
import me.chill.queries.createCheckMap
import me.chill.queries.generateString

class AreUsersFollowingPlaylistQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val ids: String) : SpotifyFollowQuery() {

	override fun execute(): Map<String, Boolean> {
		val parameters = mapOf("ids" to ids)

		val response = query("https://api.spotify.com/v1/playlists/$id/followers/contains", accessToken, parameters)

		return response.createCheckMap(ids, gson)
	}

	class Builder(private val id: String, private val accessToken: String) {
		private val users = mutableListOf<String>()

		fun addUser(user: String): Builder {
			users.add(user)
			return this
		}

		fun setUsers(users: List<String>): Builder {
			this.users.clear()
			this.users.addAll(users)
			return this
		}

		fun build(): AreUsersFollowingPlaylistQuery {
			users.checkEmpty("Users")
			users.checkListSizeLimit("Users", 5)

			return AreUsersFollowingPlaylistQuery(id, accessToken, users.generateString())
		}
	}
}