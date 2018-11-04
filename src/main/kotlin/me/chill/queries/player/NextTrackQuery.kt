package me.chill.queries.player

import khttp.post
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.generateParameters
import me.chill.utility.request.displayErrorMessage
import me.chill.utility.request.generateHeader

class NextTrackQuery private constructor(
  private val accessToken: String,
  private val deviceId: String?) : AbstractQuery<Boolean>("me", "player", "next") {

  override fun execute(): Boolean {
    val parameters = mapOf("device_id" to deviceId).generateParameters()

    val response = post(queryEndpoint, generateHeader(accessToken), parameters, "-")

    response.statusCode.takeUnless { it == 403 }?.let { displayErrorMessage(response) }

    return response.statusCode == 204
  }

  class Builder(private val accessToken: String) {
    private var device: String? = null

    fun device(device: String): Builder {
      this.device = device
      return this
    }

    fun build() = NextTrackQuery(accessToken, device)
  }
}