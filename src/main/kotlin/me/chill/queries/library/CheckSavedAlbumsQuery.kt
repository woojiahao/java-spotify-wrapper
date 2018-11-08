package me.chill.queries.library

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmpty
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.generateString
import me.chill.utility.request.createCheckMap
import me.chill.utility.request.query

class CheckSavedAlbumsQuery private constructor(
  private val accessToken: String,
  private val ids: String) : AbstractQuery<Map<String, Boolean>>("me", "albums", "contains") {

  override fun execute() = query(endpoint, accessToken, mapOf("ids" to ids)).createCheckMap(ids)

  class Builder(private val accessToken: String) {
    private val albums = mutableListOf<String>()

    fun addAlbums(vararg albums: String): Builder {
      this.albums.addAll(albums)
      return this
    }

    fun build(): CheckSavedAlbumsQuery {
      albums.checkEmpty("Albums")
      albums.checkListSizeLimit("Albums", 50)

      return CheckSavedAlbumsQuery(accessToken, albums.generateString())
    }
  }
}