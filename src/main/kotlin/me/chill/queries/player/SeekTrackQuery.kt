package me.chill.queries.player

import khttp.head
import me.chill.exceptions.SpotifyQueryException

/**
 * Contains 2 execute() methods, the second should be used as this endpoint is asynchronous
 */
class SeekTrackQuery private constructor(
	private val accessToken: String,
	private val position: Int,
	private val deviceId: String?) : SpotifyPlayerQuery() {

	fun execute(callback: (Int) -> Unit) {
		val parameters = mapOf(
			"position_ms" to position,
			"device_id" to deviceId
		).generateParameters()

		asyncRequest(Method.Put, seekEndpoint, generateHeaders(accessToken), parameters) { callback(it.statusCode) }
	}

	override fun execute(): Any? = null

	class Builder(private val accessToken: String) {
		private var position: Int? = null
		private var device: String? = null

		fun position(position: Int): Builder {
			this.position = position
			return this
		}

		fun device(device: String): Builder {
			this.device = device
			return this
		}

		fun build(): SeekTrackQuery {
			position ?: throw SpotifyQueryException("Position must be specified")

			return SeekTrackQuery(accessToken, position!!, device)
		}
	}
}