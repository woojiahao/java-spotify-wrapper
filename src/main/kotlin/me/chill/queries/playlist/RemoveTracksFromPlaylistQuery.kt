package me.chill.queries.playlist

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmptyAndSizeLimit
import me.chill.utility.extensions.conditionalMap
import me.chill.utility.request.RequestMethod

class RemoveTracksFromPlaylistQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val tracks: Set<DeleteTrack>) : AbstractQuery<Boolean>(accessToken, RequestMethod.Delete, "playlists", playlistId, "tracks") {

  private data class DeleteTrack(
    var uri: String,
    val positions: List<Int>? = null
  )

  // TODO: Check this
  override fun execute(): Boolean {
    val snapshotId = GetSinglePlaylistQuery.Builder(accessToken, playlistId).build().execute().snapshotId
    val fixed = tracks.conditionalMap({ !it.uri.startsWith("spotify:track:") }) {
      DeleteTrack("spotify:track:${it.uri}", it.positions)
    }
    val body = gson.toJson(mapOf(
      "tracks" to fixed,
      "snapshot_id" to snapshotId
    ))

    return checkedQuery(data = body, isModification = true).statusCode == 200
  }

  class Builder(private val accessToken: String, private val playlistId: String) {
    private val tracks = mutableSetOf<DeleteTrack>()

    fun addTrack(trackId: String): Builder {
      tracks.add(DeleteTrack(trackId))
      return this
    }

    fun addTrack(trackId: String, vararg positions: Int): Builder {
      tracks.add(DeleteTrack(trackId, positions.toList()))
      return this
    }

    fun build(): RemoveTracksFromPlaylistQuery {
      tracks.checkEmptyAndSizeLimit("Tracks", 100)

      return RemoveTracksFromPlaylistQuery(accessToken, playlistId, tracks)
    }
  }
}