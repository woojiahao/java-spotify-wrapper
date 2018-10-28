package me.chill.queries.follow

import khttp.delete
import me.chill.queries.checkListSizeLimit
import me.chill.queries.generateNullableString

class UnfollowUserOrArtistQuery private constructor(
	private val accessToken: String,
	private val type: String,
	private val ids: String?) : SpotifyFollowQuery() {

	override fun execute(): Boolean {
		val parameters = mapOf(
			"type" to type,
			"ids" to ids
		).generateParameters()

		val response = delete(followEndpoint, generateModificationHeaders(accessToken), parameters)
		response.responseCheck()

		return response.statusCode == 204
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

		fun build(): UnfollowUserOrArtistQuery {
			ids.checkListSizeLimit("IDs", 50)

			return UnfollowUserOrArtistQuery(accessToken, userType.name.toLowerCase(), ids.generateNullableString())
		}
	}
}