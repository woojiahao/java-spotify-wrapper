package me.chill.queries.follow

import me.chill.exceptions.SpotifyQueryException
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkSizeLimit
import me.chill.utility.extensions.generateNullableString
import me.chill.utility.extensions.splitAndAdd
import me.chill.utility.request.RequestMethod

class FollowUserOrArtistQuery private constructor(
  private val accessToken: String,
  private val userType: String,
  private val ids: Set<String>) : AbstractQuery<Boolean>(accessToken, RequestMethod.Put, "me", "following") {

  override fun execute(): Boolean {
    val parameters = mapOf(
      "type" to userType,
      "ids" to ids.generateNullableString()
    )

    return checkedQuery(parameters, isModification = true).statusCode == 204
  }

  // TODO: Pass the user type as an argument to the builder
  class Builder(private val accessToken: String) {
    private var type: UserType? = null
    private val users = mutableSetOf<String>()

    fun type(type: UserType): Builder {
      this.type = type
      return this
    }

    fun addUsers(vararg users: String): Builder {
      this.users.splitAndAdd(users)
      return this
    }

    fun build(): FollowUserOrArtistQuery {
      users.checkSizeLimit("Users", 50)

      type ?: throw SpotifyQueryException("User Type must be specified")

      return FollowUserOrArtistQuery(accessToken, type!!.name, users)
    }
  }
}