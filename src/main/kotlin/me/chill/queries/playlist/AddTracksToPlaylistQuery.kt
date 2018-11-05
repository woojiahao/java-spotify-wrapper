package me.chill.queries.playlist

import khttp.post
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.checkLower
import me.chill.utility.request.generateModificationHeader
import me.chill.utility.request.responseCheck
import me.chill.exceptions.SpotifyQueryException

/**
 * Add one or more tracks to a user’s playlist
 */
class AddTracksToPlaylistQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val uris: List<String>,
  private val position: Int?) : AbstractQuery<Pair<Boolean, String?>>("playlists", playlistId, "tracks") {

  /**
   * @throws SpotifyQueryException when the operation fails
   * @returns Pair(Boolean, String); Pair(true, <snapshot_id>) if the operation was successful; Pair(false, null) if
   * the operation failed, ie. trying to add tracks to a playlist with more than 10,000 tracks
   */
  override fun execute(): Pair<Boolean, String?> {
    val body = gson.toJson(mapOf(
      "uris" to uris,
      "position" to position
    ))

    val response = post(endpoint, generateModificationHeader(accessToken), data = body)
    response.statusCode.takeIf { it == 403 }?.let { return Pair(false, null) }
    response.responseCheck()

    return Pair(true, response.jsonObject.getString("snapshot_id"))
  }

  class Builder(private val accessToken: String, private val playlistId: String) {
    private val uris = mutableListOf<String>()
    private var position: Int? = null

    /**
     * @param position Index to insert the new set of tracks into the playlist. This index is zero-based
     */
    fun position(position: Int): Builder {
      this.position = position
      return this
    }

    /**
     * @param uri Track uri to be added to the playlist
     */
    fun addUri(uri: String): Builder {
      uris.add(uri)
      return this
    }

    /**
     * @param uris List of track uris to be added, any pre-existing values in the playlist is cleared first
     */
    fun setUris(uris: List<String>): Builder {
      this.uris.clear()
      this.uris.addAll(uris)
      return this
    }

    /**
     * @throws SpotifyQueryException if **position** is below 0
     * @throws SpotifyQueryException if there are more than 100 unique track uris
     */
    fun build(): AddTracksToPlaylistQuery {
      position?.checkLower("Position")
      uris.checkListSizeLimit("URI", 100)

      return AddTracksToPlaylistQuery(accessToken, playlistId, uris, position)
    }
  }
}