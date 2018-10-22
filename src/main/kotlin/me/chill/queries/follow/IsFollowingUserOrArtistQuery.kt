package me.chill.queries.follow

import com.google.gson.JsonArray
import me.chill.queries.SpotifyQueryException

class IsFollowingUserOrArtistQuery private constructor(
	private val accessToken: String,
	private val type: String,
	private val ids: String) : SpotifyFollowQuery() {

	enum class UserType { Artist, User }

	override fun execute(): Map<String, Boolean> {
		val parameters = mapOf(
			"type" to type,
			"ids" to ids
		)

		val response = query("${followEndpoint}contains", accessToken, parameters)

		return ids.split(",").distinct().zip(gson.fromJson(response.text, JsonArray::class.java).map { it.asBoolean }).toMap()
	}

	class Builder(private val accessToken: String) {
		private var type: UserType? = null
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

		fun type(type: UserType): Builder {
			this.type = type
			return this
		}

		fun build(): IsFollowingUserOrArtistQuery {
			if (ids.isEmpty()) throw SpotifyQueryException("ID list cannot be empty")
			if (ids.size > 50) throw SpotifyQueryException("ID list cannot contain more than 50 IDs at a time")

			type ?: throw SpotifyQueryException("A UserType must be set for a query")

			return IsFollowingUserOrArtistQuery(accessToken, type!!.name.toLowerCase(), ids.asSequence().distinct().joinToString(","))
		}
	}
}
