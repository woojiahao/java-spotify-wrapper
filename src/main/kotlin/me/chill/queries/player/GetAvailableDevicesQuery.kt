package me.chill.queries.player

import me.chill.models.Device
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.readFromJsonArray
import me.chill.utility.request.RequestMethod

class GetAvailableDevicesQuery private constructor(
  private val accessToken: String) : AbstractQuery<List<Device>>(accessToken, RequestMethod.Get, "me", "player", "devices") {

  override fun execute(): List<Device> =
    gson.readFromJsonArray("devices", checkedQuery())

  class Builder(private val accessToken: String) {
    fun build() = GetAvailableDevicesQuery(accessToken)
  }
}