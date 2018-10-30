package me.chill.queries.player

import me.chill.exceptions.SpotifyQueryException
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.generateParameters
import me.chill.utility.request.RequestMethod
import me.chill.utility.request.asyncRequest
import me.chill.utility.request.generateHeader
import me.chill.utility.request.responseCheck

/**
 * Contains 2 execute() methods, the second should be used as this endpoint is asynchronous
 */
class SeekTrackQuery private constructor(
	private val accessToken: String,
	private val position: Int,
	private val deviceId: String?) : AbstractQuery("me", "player", "seek") {

	fun execute(callback: (Int) -> Unit) {
		val parameters = mapOf(
			"position_ms" to position,
			"device_id" to deviceId
		).generateParameters()

		asyncRequest(RequestMethod.Put, queryEndpoint, generateHeader(accessToken), parameters) { callback(it.statusCode) }
	}

	@Throws(SpotifyQueryException::class)
	override fun execute(): Any? {
		val parameters = mapOf(
			"position_ms" to position,
			"device_id" to deviceId
		).generateParameters()

		asyncRequest(RequestMethod.Put, queryEndpoint, generateHeader(accessToken), parameters) { it.responseCheck() }
		return null
	}

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