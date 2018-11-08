package me.chill.queries.follow

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmpty
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.generateString
import me.chill.utility.request.createCheckMap
import me.chill.utility.request.query

class IsFollowingUserOrArtistQuery private constructor(
  private val accessToken: String,
  private val type: String,
  private val ids: String) : AbstractQuery<Map<String, Boolean>>("me", "following", "contains") {

  override fun execute(): Map<String, Boolean> {
    val parameters = mapOf(
      "type" to type,
      "ids" to ids
    )

    return query(endpoint, accessToken, parameters).createCheckMap(ids)
  }

  class Builder(private val accessToken: String, private val userType: UserType) {
    private val ids = mutableListOf<String>()

    fun addIds(vararg ids: String): Builder {
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
