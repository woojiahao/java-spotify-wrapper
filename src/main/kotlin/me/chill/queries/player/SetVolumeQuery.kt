package me.chill.queries.player

import khttp.put
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLimit
import me.chill.utility.extensions.generateParameters
import me.chill.utility.request.displayErrorMessage
import me.chill.utility.request.generateHeader

class SetVolumeQuery private constructor(
	private val accessToken: String,
	private val volumePercentage: Int,
	private val deviceId: String?) : AbstractQuery("me", "player", "volume") {

	override fun execute(): Boolean {
		val parameters = mapOf(
			"volume_percent" to volumePercentage,
			"device_id" to deviceId
		).generateParameters()

		val response = put(queryEndpoint, generateHeader(accessToken), parameters, "-")

		response.statusCode.takeUnless { it == 403 }?.let { displayErrorMessage(response) }

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