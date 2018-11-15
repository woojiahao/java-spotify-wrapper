package me.chill.queries.player

import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLimit
import me.chill.utility.request.RequestMethod
import me.chill.utility.request.responseCheck

class SetVolumeQuery private constructor(
  private val accessToken: String,
  private val volumePercentage: Int,
  private val deviceId: String?) : AbstractQuery<Boolean>(accessToken, RequestMethod.Put, "me", "player", "volume") {

  override fun execute(): Boolean {
    val parameters = mapOf(
      "volume_percent" to volumePercentage,
      "device_id" to deviceId
    )

    val response = checkedQuery(parameters, "-") { it.responseCheck(403) }

    return response.statusCode == 204
  }

  class Builder(private val accessToken: String, private val volumePercentage: Int) {
    private var device: String? = null

    fun device(device: String): Builder {
      this.device = device
      return this
    }

    fun build(): SetVolumeQuery {
      volumePercentage.checkLimit(0, 100)

      return SetVolumeQuery(accessToken, volumePercentage, device)
    }
  }
}