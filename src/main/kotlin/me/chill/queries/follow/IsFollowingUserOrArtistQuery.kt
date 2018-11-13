package me.chill.queries.follow

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.*
import me.chill.utility.request.query

class IsFollowingUserOrArtistQuery private constructor(
  private val accessToken: String,
  private val type: String,
  private val ids: Set<String>) : AbstractQuery<Map<String, Boolean>>("me", "following", "contains") {

  override fun execute(): Map<String, Boolean> {
    val parameters = mapOf(
      "type" to type,
      "ids" to ids.generateString()
    )

    return gson.createResponseMap(ids, query(endpoint, accessToken, parameters))
  }

  class Builder(private val accessToken: String, private val userType: UserType) {
    private val ids = mutableSetOf<String>()

    fun addIds(vararg ids: String): Builder {
      this.ids.splitAndAdd(ids)
      return this
    }

    fun build(): IsFollowingUserOrArtistQuery {
      ids.checkEmptyAndSizeLimit("IDs", 50)

      return IsFollowingUserOrArtistQuery(accessToken, userType.name.toLowerCase(), ids)
    }
  }
}
