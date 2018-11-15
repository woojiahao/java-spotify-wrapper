package me.chill.queries.playlist

import me.chill.exceptions.SpotifyQueryException
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLower
import me.chill.utility.extensions.checkSizeLimit
import me.chill.utility.extensions.conditionalMap
import me.chill.utility.extensions.splitAndAdd
import me.chill.utility.request.RequestMethod
import me.chill.utility.request.responseCheck

/**
 * Add one or more tracks to a user’s playlist
 */
class AddTracksToPlaylistQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val uris: Set<String>,
  private val position: Int?) : AbstractQuery<Pair<Boolean, String?>>(accessToken, RequestMethod.Post, "playlists", playlistId, "tracks") {

  /**
   * @throws SpotifyQueryException when the operation fails
   * @returns Pair(Boolean, String); Pair(true, <snapshot_id>) if the operation was successful; Pair(false, null) if
   * the operation failed, ie. trying to add tracks to a playlist with more than 10,000 tracks
   */
  override fun execute(): Pair<Boolean, String?> {
    val body = gson.toJson(mapOf(
      "uris" to uris.conditionalMap({ !it.startsWith("spotify:track:") }) { "spotify:track:$it" },
      "position" to position
    ))

    val response = checkedQuery(data = body) { it.responseCheck(403) }
    if (response.statusCode == 403) return Pair(false, null)

    return Pair(true, response.jsonObject.getString("snapshot_id"))
  }

  class Builder(private val accessToken: String, private val playlistId: String) {
    private val uris = mutableSetOf<String>()
    private var position: Int? = null

    /**
     * @param position Index to insert the new set of tracks into the playlist. This index is zero-based
     */
    fun position(position: Int): Builder {
      this.position = position
      return this
    }

    fun addUris(vararg uris: String): Builder {
      this.uris.splitAndAdd(uris)
      return this
    }

    /**
     * @throws SpotifyQueryException if **position** is below 0
     * @throws SpotifyQueryException if there are more than 100 unique track uris
     */
    fun build(): AddTracksToPlaylistQuery {
      position?.checkLower("Position")
      uris.checkSizeLimit("URI", 100)

      return AddTracksToPlaylistQuery(accessToken, playlistId, uris, position)
    }
  }
}