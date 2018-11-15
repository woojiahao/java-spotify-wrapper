package me.chill.queries.playlist

import me.chill.exceptions.SpotifyQueryException
import me.chill.models.Playlist
import me.chill.queries.AbstractQuery
import me.chill.queries.profiles.GetCurrentUserProfileQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.RequestMethod

/**
 * Create an empty playlist for the current Spotify user
 */
class CreatePlaylistQuery private constructor(
  private val accessToken: String,
  private val name: String,
  private val public: Boolean?,
  private val collaborative: Boolean?,
  private val description: String?) : AbstractQuery<Playlist>(accessToken, RequestMethod.Post, "users", "%s", "playlists") {

  /**
   * @throws SpotifyQueryException If the target playlist is already public and the user attempts to set the playlist
   * to collaborative
   * @throws SpotifyQueryException If the user id supplied is not the user that authorized the application
   * @return Newly created playlist
   */
  override fun execute(): Playlist {
    val body = gson.toJson(mapOf(
      "name" to name,
      "public" to public,
      "collaborative" to collaborative,
      "description" to description
    ))

    val currentUserId = GetCurrentUserProfileQuery.Builder(accessToken).build().execute().id

    return gson.read(checkedQuery(data = body, link = endpoint.format(currentUserId), isModification = true).text)
  }

  class Builder(private val accessToken: String, private val name: String) {
    private var public: Boolean? = null
    private var collaborative: Boolean? = null
    private var description: String? = null

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
     * @return CreatePlaylistQuery to execute the following operation with set arguments
     */
    fun build(): CreatePlaylistQuery {
      if (collaborative == true && public == true) {
        throw SpotifyQueryException("You cannot enable a playlist to be collaborative if it is public")
      }

      return CreatePlaylistQuery(accessToken, name, public, collaborative, description)
    }
  }
}