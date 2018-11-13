package me.chill.queries.library

import khttp.delete
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.*
import me.chill.utility.request.generateModificationHeader
import me.chill.utility.request.responseCheck

class RemoveSavedAlbumsQuery private constructor(
  private val accessToken: String,
  private val ids: Set<String>) : AbstractQuery<Boolean>("me", "albums") {

  override fun execute(): Boolean {
    val parameters = mapOf("ids" to ids.generateNullableString()).generateParameters()
    val response = delete(endpoint, generateModificationHeader(accessToken), parameters)
    response.responseCheck()

    return response.statusCode == 200
  }

  class Builder(private val accessToken: String) {
    private val albums = mutableSetOf<String>()

    fun addAlbums(vararg albums: String): Builder {
      this.albums.splitAndAdd(albums)
      return this
    }

    fun build(): RemoveSavedAlbumsQuery {
      albums.checkSizeLimit("Albums", 50)

      return RemoveSavedAlbumsQuery(accessToken, albums)
    }
  }
}
