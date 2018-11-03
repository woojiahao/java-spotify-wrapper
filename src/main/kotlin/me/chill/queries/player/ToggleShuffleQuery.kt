package me.chill.queries.player

import khttp.put
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.generateParameters
import me.chill.utility.request.displayErrorMessage
import me.chill.utility.request.generateHeader

class ToggleShuffleQuery private constructor(
	private val accessToken: String,
	private val state: Boolean,
	private val deviceId: String?) : AbstractQuery<Boolean>("me", "player", "shuffle") {

	override fun execute(): Boolean {
		val parameters = mapOf(
			"state" to state,
			"device_id" to deviceId
		).generateParameters()

		val response = put(queryEndpoint, generateHeader(accessToken), parameters, "-")

		response.statusCode.takeUnless { it == 403 }?.let { displayErrorMessage(response) }

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