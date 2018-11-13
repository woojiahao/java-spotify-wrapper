package me.chill.queries.follow

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.*
import me.chill.utility.request.query

class AreUsersFollowingPlaylistQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val ids: Set<String>) : AbstractQuery<Map<String, Boolean>>("playlists", playlistId, "followers", "contains") {

  override fun execute(): Map<String, Boolean> =
    gson.createResponseMap(
      ids,
      query(endpoint, accessToken, mapOf("ids" to ids.generateString()))
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