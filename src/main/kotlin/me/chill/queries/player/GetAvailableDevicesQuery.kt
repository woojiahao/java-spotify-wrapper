package me.chill.queries.player

import me.chill.models.Device
import me.chill.queries.readFromJsonArray

class GetAvailableDevicesQuery private constructor(
	private val accessToken: String) : SpotifyPlayerQuery() {

	override fun execute() =
		gson.readFromJsonArray<Device>("devices", query(availableDevicesEndpoint, accessToken))

	class Builder(private val accessToken: String) {
		fun build() = GetAvailableDevicesQuery(accessToken)
	}
}