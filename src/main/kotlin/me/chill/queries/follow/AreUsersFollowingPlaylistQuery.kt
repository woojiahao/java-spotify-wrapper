package me.chill.queries.follow

import com.google.gson.JsonArray
import me.chill.queries.SpotifyQueryException

class AreUsersFollowingPlaylistQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val ids: String): SpotifyFollowQuery() {

	override fun execute(): Map<String, Boolean> {
		val parameters = mapOf("ids" to ids)

		val response = query("https://api.spotify.com/v1/playlists/$id/followers/contains", accessToken, parameters)

		return ids.split(",").distinct().zip(gson.fromJson(response.text, JsonArray::class.java).map { it.asBoolean }).toMap()
	}

	class Builder(private val id: String, private val accessToken: String) {
		private val users = mutableListOf<String>()

		fun addUser(user: String): Builder {
			users.addAll(user.split(","))
			return this
		}

		fun setUsers(users: List<String>): Builder {
			this.users.clear()
			this.users.addAll(users)
			return this
		}

		fun build(): AreUsersFollowingPlaylistQuery {
			if (users.isEmpty()) throw SpotifyQueryException("Users list cannot be empty")
			if (users.size > 5) throw SpotifyQueryException("Users list cannot contain more than 5 users at a time")

			return AreUsersFollowingPlaylistQuery(id, accessToken, users.asSequence().distinct().joinToString(","))
		}
	}
}