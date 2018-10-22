package me.chill.queries.follow

import khttp.put
import me.chill.queries.SpotifyQueryException
import me.chill.utility.responseCheck

class FollowUserOrArtistQuery private constructor(
	private val accessToken: String,
	private val userType: String,
	private val ids: String?) : SpotifyFollowQuery() {

	override fun execute(): Boolean {
		val parameters = mapOf(
			"type" to userType,
			"ids" to ids
		)

		val headers = mapOf(
			"Authorization" to "Bearer $accessToken",
			"Content-Type" to "application/json"
		)

		val response = put(followEndpoint, headers, parameters.generateParameters())
		response.responseCheck()

		return response.statusCode == 204
	}

	class Builder(private val accessToken: String) {
		private var type: UserType? = null
		private val users = mutableListOf<String>()

		fun type(type: UserType): Builder {
			this.type = type
			return this
		}

		fun addUser(user: String): Builder {
			users.add(user)
			return this
		}

		fun setUsers(users: List<String>): Builder {
			this.users.clear()
			this.users.addAll(users)
			return this
		}

		fun build(): FollowUserOrArtistQuery {
			if (users.size > 50) throw SpotifyQueryException("Users list cannot contain more than 50 IDs")

			type ?: throw SpotifyQueryException("User Type must be specified")

			return FollowUserOrArtistQuery(accessToken, type!!.name, users.takeIf { it.isNotEmpty() }?.joinToString(","))
		}
	}
}