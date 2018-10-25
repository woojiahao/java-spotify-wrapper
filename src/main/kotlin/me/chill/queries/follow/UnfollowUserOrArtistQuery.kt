package me.chill.queries.follow

import khttp.delete
import me.chill.queries.SpotifyQueryException
import me.chill.utility.responseCheck

class UnfollowUserOrArtistQuery private constructor(
	private val accessToken: String,
	private val type: String,
	private val ids: String?) : SpotifyFollowQuery() {

	override fun execute(): Boolean {
		val parameters = mapOf(
			"type" to type,
			"ids" to ids
		).generateParameters()

		val headers = mapOf(
			"Authorization" to "Bearer $accessToken",
			"Content-Type" to "application/json"
		)

		val response = delete(followEndpoint, headers, parameters)
		response.responseCheck()

		return response.statusCode == 204
	}

	class Builder(private val accessToken: String, private val userType: UserType) {
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

		fun build(): UnfollowUserOrArtistQuery {
			if (users.size > 50) throw SpotifyQueryException("Users ID list cannot have more than 50 IDs")
			return UnfollowUserOrArtistQuery(
				accessToken,
				userType.name.toLowerCase(),
				users.takeIf { it.isNotEmpty() }?.joinToString(",")
			)
		}
	}
}