package me.chill.queries.player

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import khttp.put
import me.chill.exceptions.SpotifyQueryException
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLower
import me.chill.utility.extensions.generateParameters
import me.chill.utility.request.displayErrorMessage
import me.chill.utility.request.generateHeader

class PlayTrackQuery private constructor(
	private val accessToken: String,
	private val deviceId: String?,
	private val contextUri: String?,
	private val uris: List<String>?,
	private val offsetPosition: Int?,
	private val offsetUri: String?,
	private val position: Int?) : AbstractQuery<Boolean>("me", "player", "play") {

	private data class Offset(
		val position: Int?,
		val uri: String?
	)

	private data class Body(
		val contextUri: String?,
		val uris: List<String>?,
		val offset: Offset?,
		@SerializedName("position_ms") val position: Int?
	)

	override fun execute(): Boolean {
		val parameters = mapOf("device_id" to deviceId).generateParameters()

		val body = gson.toJson(Body(contextUri, uris, Offset(offsetPosition, offsetUri), position))

		val response = put(queryEndpoint, generateHeader(accessToken), parameters, body)

		response.statusCode.takeUnless { it == 403 }?.let { displayErrorMessage(response) }

		return response.statusCode == 204
	}

	class Builder(private val accessToken: String) {
		private var device: String? = null
		private var contextUri: String? = null
		private var offsetPosition: Int? = null
		private var offsetUri: String? = null
		private var position: Int? = null

		private val uris = mutableListOf<String>()

		fun device(device: String): Builder {
			this.device = device
			return this
		}

		fun contextUri(contextUri: String): Builder {
			this.contextUri = contextUri
			return this
		}

		fun addUri(uri: String): Builder {
			uris.add(uri)
			return this
		}

		fun setUris(uris: List<String>): Builder {
			this.uris.clear()
			this.uris.addAll(uris)
			return this
		}

		fun offsetPosition(offsetPosition: Int): Builder {
			this.offsetPosition = offsetPosition
			return this
		}

		fun offsetUri(offsetUri: String): Builder {
			this.offsetUri = offsetUri
			return this
		}

		fun position(position: Int): Builder {
			this.position = position
			return this
		}

		fun build(): PlayTrackQuery {
			val bothUriTypesArePresent = contextUri != null && uris.isNotEmpty()
			if (bothUriTypesArePresent) {
				throw SpotifyQueryException("Cannot specify context uris and a set of uris at the same time")
			}

			val isOffsetSet = offsetUri != null || offsetPosition != null
			if (isOffsetSet) {
				val noUriPresent = contextUri == null && uris.isEmpty()
				if (noUriPresent) {
					throw SpotifyQueryException("You have to use some uris to set the offsets")
				}

				val contextUriMatch = contextUri != null && (contextUri!!.contains("album") || contextUri!!.contains("playlist"))
				if (contextUriMatch) {
					val bothOffsetPropertyArePresent = offsetUri != null && offsetPosition != null
					if (bothOffsetPropertyArePresent) {
						throw SpotifyQueryException("When using a context uri, you can only specify the offset position or uri one at a time, not both")
					}
				}
			}

			position?.checkLower("Position")
			offsetPosition?.checkLower("Offset Position")

			return PlayTrackQuery(accessToken, device, contextUri, uris, offsetPosition, offsetUri, position)
		}
	}
}