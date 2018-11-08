package me.chill.queries.follow

import me.chill.exceptions.SpotifyQueryException
import me.chill.queries.AbstractQuery
import me.chill.sample.queries.UserStore.user
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.generateNullableString
import me.chill.utility.request.put

class FollowUserOrArtistQuery private constructor(
  private val accessToken: String,
  private val userType: String,
  private val ids: String?) : AbstractQuery<Boolean>("me", "following") {

  override fun execute(): Boolean {
    val parameters = mapOf(
      "type" to userType,
      "ids" to ids
    )

    val response = put(endpoint, accessToken, parameters)

    return response.statusCode == 204
  }

  class Builder(private val accessToken: String) {
    private var type: UserType? = null
    private val users = mutableListOf<String>()

    fun type(type: UserType): Builder {
      this.type = type
      return this
    }

    fun addUsers(vararg users: String): Builder {
      this.users.addAll(users)
      return this
    }

    fun build(): FollowUserOrArtistQuery {
      users.checkListSizeLimit("Users", 50)

      type ?: throw SpotifyQueryException("User Type must be specified")

      return FollowUserOrArtistQuery(accessToken, type!!.name, users.generateNullableString())
    }
  }
}