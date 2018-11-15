package me.chill.queries.player

import me.chill.queries.AbstractQuery
import me.chill.utility.request.RequestMethod
import me.chill.utility.request.responseCheck

class TransferPlaybackQuery private constructor(
  private val accessToken: String,
  private val device: String,
  private val play: Boolean?) : AbstractQuery<Boolean>(accessToken, RequestMethod.Put, "me", "player") {

  override fun execute(): Boolean {
    val parameters = mapOf(
      "device_ids" to "[\"$device\"]",
      "play" to play
    )

    val response = checkedQuery(parameters, isModification = true) { it.responseCheck(403) }

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