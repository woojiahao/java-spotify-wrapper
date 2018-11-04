package me.chill.queries.playlist

import khttp.put
import me.chill.exceptions.SpotifyQueryException
import me.chill.queries.AbstractQuery
import me.chill.utility.request.generateModificationHeader
import me.chill.utility.request.responseCheck

/**
 * Change a user-owned playlist’s name and public/private state.
 */
class ChangePlaylistDetailsQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val name: String?,
  private val public: Boolean?,
  private val collaborative: Boolean?,
  private val description: String?) : AbstractQuery<Boolean>("playlists", playlistId) {

  /**
   * @throws SpotifyQueryException If the target playlist is already public, and the user attempts to set the playlist
   * to collaborative
   * @return Boolean status to indicate if the operation was successful (true) or not (false)
   */
  override fun execute(): Boolean {
    val body = gson.toJson(mapOf(
      "name" to name,
      "public" to public,
      "collaborative" to collaborative,
      "description" to description
    ))

    val response = put(queryEndpoint, generateModificationHeader(accessToken), data = body)
    response.responseCheck()

    return response.statusCode == 200
  }

  class Builder(private val accessToken: String, private val playlistId: String) {
    private var name: String? = null
    private var public: Boolean? = null
    private var collaborative: Boolean? = null
    private var description: String? = null

    /**
     * @param name New name for the playlist
     */
    fun name(name: String): Builder {
      this.name = name
      return this
    }

    /**
     * @param description Playlist description as displayed in Spotify clients and web API
     */
    fun description(description: String): Builder {
      this.description = description
      return this
    }

    /**
     * @param collaborative If true, other users will be able to modify the playlist; Collaborative can only be set to
     * true for non-public playlists
     */
    fun collaborative(collaborative: Boolean): Builder {
      this.collaborative = collaborative
      return this
    }

    /**
     * @param public If true, the playlist will become public, else, it will become private
     */
    fun isPublic(public: Boolean): Builder {
      this.public = public
      return this
    }

    /**
     * @throws SpotifyQueryException If the user attempts to set both the public and collaborative fields to true, an
     * exception is raised since a collaborative playlist cannot be public; If the playlist if public before this
     * operation and the collaborative flag is set to true during the operation, this exception is not thrown and will
     * only be caught when the code is executed.
     * @return ChangePlaylistDetailsQuery to execute the following operations with the set arguments
     */
    fun build(): ChangePlaylistDetailsQuery {
      if (collaborative == true && public == true) {
        throw SpotifyQueryException("You cannot enable a playlist to be collaborative if it is public")
      }

      return ChangePlaylistDetailsQuery(accessToken, playlistId, name, public, collaborative, description)
    }
  }
}