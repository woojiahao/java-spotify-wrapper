package me.chill.queries.library

import khttp.delete
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.generateNullableString
import me.chill.utility.extensions.generateParameters
import me.chill.utility.request.generateModificationHeader
import me.chill.utility.request.responseCheck

class RemoveSavedAlbumsQuery private constructor(
  private val accessToken: String,
  private val ids: String?) : AbstractQuery<Boolean>("me", "albums") {

  override fun execute(): Boolean {
    val parameters = mapOf("ids" to ids).generateParameters()
    val response = delete(endpoint, generateModificationHeader(accessToken), parameters)
    response.responseCheck()

    return response.statusCode == 200
  }

  class Builder(private val accessToken: String) {
    private val albums = mutableListOf<String>()

    fun addAlbums(vararg albums: String): Builder {
      this.albums.addAll(albums)
      return this
    }

    fun build(): RemoveSavedAlbumsQuery {
      albums.checkListSizeLimit("Albums", 50)

      return RemoveSavedAlbumsQuery(accessToken, albums.generateNullableString())
    }
  }
}
