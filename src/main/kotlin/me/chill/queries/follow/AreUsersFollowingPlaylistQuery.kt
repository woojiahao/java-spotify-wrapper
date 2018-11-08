package me.chill.queries.follow

import me.chill.queries.AbstractQuery
import me.chill.sample.queries.UserStore.user
import me.chill.utility.extensions.checkEmpty
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.generateString
import me.chill.utility.request.createCheckMap
import me.chill.utility.request.query

class AreUsersFollowingPlaylistQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val ids: String) : AbstractQuery<Map<String, Boolean>>("playlists", playlistId, "followers", "contains") {

  override fun execute() = query(endpoint, accessToken, mapOf("ids" to ids)).createCheckMap(ids)

  class Builder(private val accessToken: String, private val playlistId: String) {
    private val users = mutableListOf<String>()

    fun addUsers(vararg users: String): Builder {
      this.users.addAll(users)
      return this
    }

    fun build(): AreUsersFollowingPlaylistQuery {
      users.checkEmpty("Users")
      users.checkListSizeLimit("Users", 5)

      return AreUsersFollowingPlaylistQuery(accessToken, playlistId, users.generateString())
    }
  }
}