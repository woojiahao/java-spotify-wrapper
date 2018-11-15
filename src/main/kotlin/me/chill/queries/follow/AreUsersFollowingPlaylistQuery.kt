package me.chill.queries.follow

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmptyAndSizeLimit
import me.chill.utility.extensions.createResponseMap
import me.chill.utility.extensions.generateString
import me.chill.utility.extensions.splitAndAdd
import me.chill.utility.request.RequestMethod

class AreUsersFollowingPlaylistQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val ids: Set<String>) : AbstractQuery<Map<String, Boolean>>(accessToken, RequestMethod.Get, "playlists", playlistId, "followers", "contains") {

  override fun execute(): Map<String, Boolean> =
    gson.createResponseMap(
      ids,
      checkedQuery(mapOf("ids" to ids.generateString()))
    )

  class Builder(private val accessToken: String, private val playlistId: String) {
    private val users = mutableSetOf<String>()

    fun addUsers(vararg users: String): Builder {
      this.users.splitAndAdd(users)
      return this
    }

    fun build(): AreUsersFollowingPlaylistQuery {
      users.checkEmptyAndSizeLimit("Users", 5)

      return AreUsersFollowingPlaylistQuery(accessToken, playlistId, users)
    }
  }
}