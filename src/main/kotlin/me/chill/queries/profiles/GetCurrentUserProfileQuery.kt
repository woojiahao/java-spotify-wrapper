package me.chill.queries.profiles

import me.chill.models.User
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.RequestMethod

/**
 * Get detailed profile information about the current user
 */
class GetCurrentUserProfileQuery private constructor(private val accessToken: String) : AbstractQuery<User>(accessToken, RequestMethod.Get, "me") {

  /**
   * @throws SpotifyQueryException When requesting fields that you don’t have the user’s authorization to access
   * @return Current user's profile
   */
  override fun execute(): User =
    gson.read(checkedQuery().text)

  class Builder(private val accessToken: String) {
    fun build() = GetCurrentUserProfileQuery(accessToken)
  }
}