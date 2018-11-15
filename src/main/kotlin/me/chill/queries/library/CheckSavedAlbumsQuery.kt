package me.chill.queries.library

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmptyAndSizeLimit
import me.chill.utility.extensions.createResponseMap
import me.chill.utility.extensions.generateString
import me.chill.utility.extensions.splitAndAdd
import me.chill.utility.request.RequestMethod

class CheckSavedAlbumsQuery private constructor(
  private val accessToken: String,
  private val ids: Set<String>) : AbstractQuery<Map<String, Boolean>>(accessToken, RequestMethod.Get, "me", "albums", "contains") {

  override fun execute(): Map<String, Boolean> =
    gson.createResponseMap(
      ids,
      checkedQuery(mapOf("ids" to ids.generateString()))
    )

  class Builder(private val accessToken: String) {
    private val albums = mutableSetOf<String>()

    fun addAlbums(vararg albums: String): Builder {
      this.albums.splitAndAdd(albums)
      return this
    }

    fun build(): CheckSavedAlbumsQuery {
      albums.checkEmptyAndSizeLimit("Albums", 50)

      return CheckSavedAlbumsQuery(accessToken, albums)
    }
  }
}