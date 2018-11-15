package me.chill.queries.follow

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkSizeLimit
import me.chill.utility.extensions.generateNullableString
import me.chill.utility.extensions.splitAndAdd
import me.chill.utility.request.RequestMethod

class UnfollowUserOrArtistQuery private constructor(
  private val accessToken: String,
  private val type: String,
  private val ids: Set<String>) : AbstractQuery<Boolean>(accessToken, RequestMethod.Delete, "me", "following") {

  override fun execute(): Boolean {
    val parameters = mapOf(
      "type" to type,
      "ids" to ids.generateNullableString()
    )

    return checkedQuery(parameters, isModification = true).statusCode == 204
  }

  class Builder(private val accessToken: String, private val userType: UserType) {
    private val ids = mutableSetOf<String>()

    fun addIds(vararg ids: String): Builder {
      this.ids.splitAndAdd(ids)
      return this
    }

    fun build(): UnfollowUserOrArtistQuery {
      ids.checkSizeLimit("IDs", 50)

      return UnfollowUserOrArtistQuery(accessToken, userType.name.toLowerCase(), ids)
    }
  }
}