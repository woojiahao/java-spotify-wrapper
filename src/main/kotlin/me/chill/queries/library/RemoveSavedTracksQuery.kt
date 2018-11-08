package me.chill.queries.library

import khttp.delete
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.generateNullableString
import me.chill.utility.extensions.generateParameters
import me.chill.utility.request.generateModificationHeader
import me.chill.utility.request.responseCheck

class RemoveSavedTracksQuery private constructor(
  private val accessToken: String,
  private val ids: String?) : AbstractQuery<Boolean>("me", "albums") {

  override fun execute(): Boolean {
    val parameters = mapOf("ids" to ids).generateParameters()
    val response = delete(endpoint, generateModificationHeader(accessToken), parameters)
    response.responseCheck()

    return response.statusCode == 200
  }

  class Builder(private val accessToken: String) {
    private val tracks = mutableListOf<String>()

    fun addTracks(vararg tracks: String): Builder {
      this.tracks.addAll(tracks)
      return this
    }

    fun build(): RemoveSavedTracksQuery {
      tracks.checkListSizeLimit("tracks", 50)

      return RemoveSavedTracksQuery(accessToken, tracks.generateNullableString())
    }
  }
}