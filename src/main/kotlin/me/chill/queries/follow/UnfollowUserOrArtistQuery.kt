package me.chill.queries.follow

import khttp.delete
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.*
import me.chill.utility.request.generateModificationHeader
import me.chill.utility.request.responseCheck

class UnfollowUserOrArtistQuery private constructor(
  private val accessToken: String,
  private val type: String,
  private val ids: Set<String>) : AbstractQuery<Boolean>("me", "following") {

  override fun execute(): Boolean {
    val parameters = mapOf(
      "type" to type,
      "ids" to ids.generateNullableString()
    ).generateParameters()

    val response = delete(endpoint, generateModificationHeader(accessToken), parameters)
    response.responseCheck()

    return response.statusCode == 204
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