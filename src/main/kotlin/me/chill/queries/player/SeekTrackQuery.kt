package me.chill.queries.player

import khttp.put
import me.chill.exceptions.SpotifyQueryException
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.generateParameters
import me.chill.utility.request.displayErrorMessage
import me.chill.utility.request.generateHeader

/*
TODO: Optionally, override the executeAsync() method to invoke the threading and after the system has been returned, perform checking if the track was actually modified by re-querying some other endpoint
 */
class SeekTrackQuery private constructor(
  private val accessToken: String,
  private val position: Int,
  private val deviceId: String?) : AbstractQuery<Boolean>("me", "player", "seek") {

  /**
   * Seeking a track returns the status of the seek
   *
   * See {@linktourl https://developer.spotify.com/documentation/web-api/reference/player/seek-to-position-in-currently-playing-track/}
   */
  override fun execute(): Boolean {
    val parameters = mapOf(
      "position_ms" to position,
      "device_id" to deviceId
    ).generateParameters()

    val response = put(queryEndpoint, generateHeader(accessToken), parameters, "-")

    response.statusCode.takeUnless { it == 403 }?.let { displayErrorMessage(response) }
    return response.statusCode == 204
  }

  class Builder(private val accessToken: String) {
    private var position: Int? = null
    private var device: String? = null

    fun position(position: Int): Builder {
      this.position = position
      return this
    }

    fun device(device: String): Builder {
      this.device = device
      return this
    }

    fun build(): SeekTrackQuery {
      position ?: throw SpotifyQueryException("Position must be specified")

      return SeekTrackQuery(accessToken, position!!, device)
    }
  }
}