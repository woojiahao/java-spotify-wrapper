package me.chill.queries.library

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.generateNullableString
import me.chill.utility.request.put

class SaveAlbumsQuery private constructor(
  private val accessToken: String,
  private val ids: String?) : AbstractQuery<Boolean>("me", "albums") {

  override fun execute() = put(endpoint, accessToken, mapOf("ids" to ids)).statusCode == 201

  class Builder(private val accessToken: String) {
    private val albums = mutableListOf<String>()

    fun addAlbums(vararg albums: String): Builder {
      this.albums.addAll(albums)
      return this
    }

    fun build(): SaveAlbumsQuery {
      albums.checkListSizeLimit("Albums", 50)

      return SaveAlbumsQuery(accessToken, albums.generateNullableString())
    }
  }
}