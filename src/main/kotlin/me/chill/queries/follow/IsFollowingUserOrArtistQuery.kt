package me.chill.queries.follow

import me.chill.queries.checkEmpty
import me.chill.queries.checkListSizeLimit
import me.chill.queries.createCheckMap
import me.chill.queries.generateString

class IsFollowingUserOrArtistQuery private constructor(
	private val accessToken: String,
	private val type: String,
	private val ids: String) : SpotifyFollowQuery() {

	override fun execute(): Map<String, Boolean> {
		val parameters = mapOf(
			"type" to type,
			"ids" to ids
		)

		val response = query("${followEndpoint}contains", accessToken, parameters)

		return response.createCheckMap(ids, gson)
	}

	class Builder(private val accessToken: String, private val userType: UserType) {
		private val ids = mutableListOf<String>()

		fun addId(id: String): Builder {
			ids.add(id)
			return this
		}

		fun setIds(ids: List<String>): Builder {
			this.ids.clear()
			this.ids.addAll(ids)
			return this
		}

		fun build(): IsFollowingUserOrArtistQuery {
			ids.checkEmpty("IDs")
			ids.checkListSizeLimit("IDs", 50)

			return IsFollowingUserOrArtistQuery(accessToken, userType.name.toLowerCase(), ids.generateString())
		}
	}
}
