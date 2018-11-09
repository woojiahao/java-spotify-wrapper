package me.chill.queries.playlist

import khttp.delete
import me.chill.exceptions.SpotifyQueryException
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.conditionalMap
import me.chill.utility.request.generateModificationHeader
import me.chill.utility.request.responseCheck

class RemoveTracksFromPlaylistQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val tracks: List<DeleteTrack>) : AbstractQuery<Boolean>("playlists", playlistId, "tracks") {

  private data class DeleteTrack(
    var uri: String,
    val positions: List<Int>? = null
  )

  override fun execute(): Boolean {
    val snapshotId = GetSinglePlaylistQuery.Builder(accessToken, playlistId).build().execute().snapshotId
    val fixed = tracks.conditionalMap({ !it.uri.startsWith("spotify:track:") }) {
      DeleteTrack("spotify:track:${it.uri}", it.positions)
    }
    val body = gson.toJson(mapOf(
      "tracks" to fixed,
      "snapshot_id" to snapshotId
    ))

    val response = delete(endpoint, generateModificationHeader(accessToken), data = body)
    response.responseCheck()

    return response.statusCode == 200
  }

  class Builder(private val accessToken: String, private val playlistId: String) {
    private val tracks = mutableListOf<DeleteTrack>()

    fun addTrack(trackId: String): Builder {
      tracks.add(DeleteTrack(trackId))
      return this
    }

    fun addTrack(trackId: String, vararg positions: Int): Builder {
      tracks.add(DeleteTrack(trackId, positions.toList()))
      return this
    }

    fun build(): RemoveTracksFromPlaylistQuery {
      tracks.ifEmpty { throw SpotifyQueryException("Tracks cannot be empty") }
      tracks.size.takeIf { it > 100 }?.let { throw SpotifyQueryException("Tracks cannot exceed 100") }

      return RemoveTracksFromPlaylistQuery(accessToken, playlistId, tracks)
    }
  }
}