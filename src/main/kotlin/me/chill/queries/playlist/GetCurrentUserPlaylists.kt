package me.chill.queries.playlist

import me.chill.models.Paging
import me.chill.models.Playlist
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLimit
import me.chill.utility.extensions.checkRange
import me.chill.utility.extensions.read
import me.chill.utility.request.query

/**
 * Get a list of the playlists owned or followed by the current Spotify user
 */
class GetCurrentUserPlaylists private constructor(
  private val accessToken: String,
  private val limit: Int,
  private val offset: Int) : AbstractQuery<Paging<Playlist>>("me", "playlists") {

  /**
   * @throws SpotifyQueryException Operation was a failure, error code will be displayed
   * @return Requested list of playlists, List<Playlist>
   */
  override fun execute(): Paging<Playlist> {
    val parameters = mapOf(
      "limit" to limit,
      "offset" to offset
    )

    val response = query(queryEndpoint, accessToken, parameters)

    return gson.read(response.text)
  }

  class Builder(private val accessToken: String) {
    private var limit = 20
    private var offset = 0

    /**
     * @param limit The maximum number of playlists to return, between 1 to 50 (inclusive)
     */
    fun limit(limit: Int): Builder {
      this.limit = limit
      return this
    }

    /**
     * @param offset The index of the first playlist to return, between 0 to 100,000 (inclusive)
     */
    fun offset(offset: Int): Builder {
      this.offset = offset
      return this
    }

    /**
     * @throws SpotifyQueryException **limit** was not between 1 to 50
     * @throws SpotifyQueryException **offset** was not between 0 to 100,000
     * @return CreatePlaylistQuery to execute the following operation with set arguments
     */
    fun build(): GetCurrentUserPlaylists {
      limit.checkLimit()
      offset.checkRange("Offset", 0, 100000)

      return GetCurrentUserPlaylists(accessToken, limit, offset)
    }
  }
}