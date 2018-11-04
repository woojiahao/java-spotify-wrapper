package me.chill.queries.player

import khttp.put
import me.chill.models.RepeatState
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.generateParameters
import me.chill.utility.request.displayErrorMessage
import me.chill.utility.request.generateHeader

class SetRepeatModeQuery private constructor(
  private val accessToken: String,
  private val state: RepeatState,
  private val deviceId: String?) : AbstractQuery<Boolean>("me", "player", "repeat") {

  override fun execute(): Boolean {
    val parameters = mapOf(
      "device_id" to deviceId,
      "state" to state.name.toLowerCase()
    ).generateParameters()

    val response = put(endpoint, generateHeader(accessToken), parameters, "-")

    response.statusCode.takeUnless { it == 403 }?.let { displayErrorMessage(response) }

    return response.statusCode == 204
  }

  class Builder(private val accessToken: String, private val state: RepeatState) {
    private var device: String? = null

    fun device(device: String): Builder {
      this.device = device
      return this
    }

    fun build() = SetRepeatModeQuery(accessToken, state, device)
  }
}