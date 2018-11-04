package me.chill.queries.playlist

import me.chill.models.Paging
import me.chill.models.Playlist
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLimit
import me.chill.utility.extensions.checkRange
import me.chill.utility.extensions.read
import me.chill.utility.request.query

/**
 * Get a list of the playlists owned or followed by a Spotify user
 */
class GetUserPlaylistsQuery private constructor(
  private val accessToken: String,
  private val userId: String,
  private val limit: Int,
  private val offset: Int) : AbstractQuery<Paging<Playlist>>("users", userId, "playlists") {

  /**
   * @throws SpotifyQueryException if the operation fails
   * @return Paging of playlists
   */
  override fun execute(): Paging<Playlist> {
    val parameters = mapOf(
      "limit" to limit,
      "offset" to offset
    )

    return gson.read(query(endpoint, accessToken, parameters).text)
  }

  class Builder(private val accessToken: String, private val userId: String) {
    private var limit = 20
    private var offset = 0

    /**
     * @param limit The maximum number of playlists to return. Default: 20. Minimum: 1. Maximum: 50
     */
    fun limit(limit: Int): Builder {
      this.limit = limit
      return this
    }

    /**
     * @param offset The index of the first playlist to return. Default: 0. Maximum: 100,000
     */
    fun offset(offset: Int): Builder {
      this.offset = offset
      return this
    }

    /**
     * @throws SpotifyQueryException when the limit is not between 1 and 50
     * @throws SpotifyQueryException when the offset is not between 0 and 100,000
     */
    fun build(): GetUserPlaylistsQuery {
      limit.checkLimit()
      offset.checkRange("Offset", 0, 100000)

      return GetUserPlaylistsQuery(accessToken, userId, limit, offset)
    }
  }
}