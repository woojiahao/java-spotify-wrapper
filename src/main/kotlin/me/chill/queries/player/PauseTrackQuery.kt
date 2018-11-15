package me.chill.queries.player

import me.chill.queries.AbstractQuery
import me.chill.utility.request.RequestMethod
import me.chill.utility.request.responseCheck

class PauseTrackQuery private constructor(
  private val accessToken: String,
  private val deviceId: String?) : AbstractQuery<Boolean>(accessToken, RequestMethod.Put, "me", "player", "pause") {

  override fun execute(): Boolean {
    val response = checkedQuery(mapOf("device_id" to deviceId), "-") { it.responseCheck(403) }

    return response.statusCode == 204
  }

  class Builder(private val accessToken: String) {
    private var device: String? = null

    fun device(device: String): Builder {
      this.device = device
      return this
    }

    fun build() = PauseTrackQuery(accessToken, device)
  }
}