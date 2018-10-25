package me.chill.queries.follow

import com.google.gson.JsonArray
import me.chill.exceptions.SpotifyQueryException
import me.chill.queries.checkEmpty
import me.chill.queries.checkLimit
import me.chill.queries.generateString

class AreUsersFollowingPlaylistQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val ids: String) : SpotifyFollowQuery() {

	override fun execute(): Map<String, Boolean> {
		val parameters = mapOf("ids" to ids)

		val response = query("https://api.spotify.com/v1/playlists/$id/followers/contains", accessToken, parameters)

		return ids.split(",").zip(gson.fromJson(response.text, JsonArray::class.java).map { it.asBoolean }).toMap()
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
			users.checkLimit("Users", 5)

			return AreUsersFollowingPlaylistQuery(id, accessToken, users.generateString())
		}
	}
}