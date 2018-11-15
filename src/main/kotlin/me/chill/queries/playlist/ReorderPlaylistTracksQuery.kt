package me.chill.queries.playlist

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLower
import me.chill.utility.request.RequestMethod

class ReorderPlaylistTracksQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val rangeStart: Int,
  private val rangeLength: Int,
  private val insertBefore: Int) : AbstractQuery<Boolean>(accessToken, RequestMethod.Put, "playlists", playlistId, "tracks") {

  override fun execute(): Boolean {
    val snapshotId = GetSinglePlaylistQuery.Builder(accessToken, playlistId).build().execute().snapshotId
    val body = gson.toJson(mapOf(
      "range_start" to rangeStart,
      "range_length" to rangeLength,
      "insert_before" to insertBefore,
      "snapshot_id" to snapshotId
    ))

    return checkedQuery(data = body, isModification = true).statusCode == 200
  }

  class Builder(private val accessToken: String, private val playlistId: String) {
    private var rangeStart = 0
    private var rangeLength = 1
    private var insertBefore = 0

    fun rangeStart(rangeStart: Int): Builder {
      this.rangeStart = rangeStart
      return this
    }

    fun rangeLength(rangeLength: Int): Builder {
      this.rangeLength = rangeLength
      return this
    }

    fun insertBefore(insertBefore: Int): Builder {
      this.insertBefore = insertBefore
      return this
    }

    fun build(): ReorderPlaylistTracksQuery {
      rangeStart.checkLower("Range Start")
      rangeLength.checkLower("Range Length")
      insertBefore.checkLower("Insert Before")

      return ReorderPlaylistTracksQuery(accessToken, playlistId, rangeStart, rangeLength, insertBefore)
    }
  }
}