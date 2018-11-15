package me.chill.queries.player

import me.chill.queries.AbstractQuery
import me.chill.utility.request.RequestMethod
import me.chill.utility.request.responseCheck

class ToggleShuffleQuery private constructor(
  private val accessToken: String,
  private val state: Boolean,
  private val deviceId: String?) : AbstractQuery<Boolean>(accessToken, RequestMethod.Put, "me", "player", "shuffle") {

  override fun execute(): Boolean {
    val parameters = mapOf(
      "state" to state,
      "device_id" to deviceId
    )

    val response = checkedQuery(parameters, "-") { it.responseCheck(403) }

    return response.statusCode == 204
  }

  class Builder(private val accessToken: String, private val state: Boolean) {
    private var device: String? = null

    fun device(device: String): Builder {
      this.device = device
      return this
    }

    fun build() = ToggleShuffleQuery(accessToken, state, device)
  }
}