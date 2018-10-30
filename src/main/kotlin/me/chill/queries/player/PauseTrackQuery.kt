package me.chill.queries.player

import me.chill.queries.AbstractQuery

class PauseTrackQuery private constructor(
	private val accessToken: String,
	private val deviceId: String?) : AbstractQuery("me", "player", "pause") {

	override fun execute(): Any? {
		val parameters = mapOf("device_id" to deviceId)
		val headers = mapOf(
			"Authorization" to "Bearer $accessToken",
			"Content-Length" to "0"
		)

		println("Pausing")
//		asyncRequest(Method.Put, "https://api.spotify.com/v1/me/player/pause", headers, parameters.generateParameters()) {
//			println(it.request.method)
//			println(it.text)
////			it.responseCheck()
//		}

		return emptyArray<String>()
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