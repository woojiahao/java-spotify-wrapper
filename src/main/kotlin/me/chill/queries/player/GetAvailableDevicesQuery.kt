package me.chill.queries.player

import me.chill.models.Device
import me.chill.queries.AbstractQuery
import me.chill.utility.request.query
import me.chill.utility.request.readFromJsonArray

class GetAvailableDevicesQuery private constructor(
  private val accessToken: String) : AbstractQuery<List<Device>>("me", "player", "devices") {

  override fun execute() = gson.readFromJsonArray<Device>("devices", query(endpoint, accessToken))

  class Builder(private val accessToken: String) {
    fun build() = GetAvailableDevicesQuery(accessToken)
  }
}