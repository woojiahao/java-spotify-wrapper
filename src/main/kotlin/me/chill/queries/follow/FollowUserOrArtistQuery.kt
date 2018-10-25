package me.chill.queries.follow

import me.chill.exceptions.SpotifyQueryException
import me.chill.queries.checkLimit
import me.chill.queries.generateNullableString

class FollowUserOrArtistQuery private constructor(
	private val accessToken: String,
	private val userType: String,
	private val ids: String?) : SpotifyFollowQuery() {

	override fun execute(): Boolean {
		val parameters = mapOf(
			"type" to userType,
			"ids" to ids
		)

		val response = put(followEndpoint, accessToken, parameters)

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
			users.checkLimit("Users", 50)

			type ?: throw SpotifyQueryException("User Type must be specified")

			return FollowUserOrArtistQuery(accessToken, type!!.name, users.generateNullableString())
		}
	}
}