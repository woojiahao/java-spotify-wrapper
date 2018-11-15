package me.chill.queries.library

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkSizeLimit
import me.chill.utility.extensions.generateNullableString
import me.chill.utility.extensions.splitAndAdd
import me.chill.utility.request.RequestMethod

class RemoveSavedAlbumsQuery private constructor(
  private val accessToken: String,
  private val ids: Set<String>) : AbstractQuery<Boolean>(accessToken, RequestMethod.Delete, "me", "albums") {

  override fun execute() =
    checkedQuery(mapOf("ids" to ids.generateNullableString()), isModification = true).statusCode == 200

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
