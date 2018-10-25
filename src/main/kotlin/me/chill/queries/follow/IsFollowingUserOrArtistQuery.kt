package me.chill.queries.follow

import com.google.gson.JsonArray
import me.chill.exceptions.SpotifyQueryException
import me.chill.queries.checkEmpty
import me.chill.queries.checkLimit
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

		return ids.split(",").zip(gson.fromJson(response.text, JsonArray::class.java).map { it.asBoolean }).toMap()
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
			ids.checkLimit("IDs", 50)

			return IsFollowingUserOrArtistQuery(accessToken, userType.name.toLowerCase(), ids.generateString())
		}
	}
}
