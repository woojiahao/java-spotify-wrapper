package me.chill.queries.player

import khttp.put
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.generateParameters
import me.chill.utility.request.displayErrorMessage
import me.chill.utility.request.generateHeader

class TransferPlaybackQuery private constructor(
  private val accessToken: String,
  private val device: String,
  private val play: Boolean?) : AbstractQuery<Boolean>("me", "player") {

  override fun execute(): Boolean {
    val parameters = mapOf(
      "device_ids" to "[\"$device\"]",
      "play" to play
    ).generateParameters()

    val response = put(endpoint, generateHeader(accessToken), parameters, "-")

    response.statusCode.takeUnless { it == 403 }?.let { displayErrorMessage(response) }

    return response.statusCode == 204
  }

  class Builder(private val accessToken: String, private val device: String) {
    private var play: Boolean? = null

    fun play(play: Boolean): Builder {
      this.play = play
      return this
    }

    fun build() = TransferPlaybackQuery(accessToken, device, play)
  }
}