package me.chill.queries.follow

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmpty
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.generateString
import me.chill.utility.request.createCheckMap
import me.chill.utility.request.query

class AreUsersFollowingPlaylistQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val ids: String) : AbstractQuery<Map<String, Boolean>>("playlists", id, "followers", "contains") {

	override fun execute() = query(queryEndpoint, accessToken, mapOf("ids" to ids)).createCheckMap(ids)

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