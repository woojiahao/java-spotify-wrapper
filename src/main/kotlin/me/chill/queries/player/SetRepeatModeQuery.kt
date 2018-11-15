package me.chill.queries.player

import me.chill.models.RepeatState
import me.chill.queries.AbstractQuery
import me.chill.utility.request.RequestMethod
import me.chill.utility.request.responseCheck

class SetRepeatModeQuery private constructor(
  private val accessToken: String,
  private val state: RepeatState,
  private val deviceId: String?) : AbstractQuery<Boolean>(accessToken, RequestMethod.Put, "me", "player", "repeat") {

  override fun execute(): Boolean {
    val parameters = mapOf(
      "device_id" to deviceId,
      "state" to state.name.toLowerCase()
    )

    val response = checkedQuery(parameters, "-") { it.responseCheck(403) }

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