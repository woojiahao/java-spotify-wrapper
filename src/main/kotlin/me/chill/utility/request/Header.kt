package me.chill.utility.request

import me.chill.exceptions.SpotifyQueryException
import me.chill.utility.extensions.generateParameters

class Header private constructor(
	private val accessToken: String,
	private val contentType: String?) {

	fun generate() =
		mapOf(
			"Authorization" to "Bearer $accessToken",
			"Content-Type" to contentType
		).generateParameters()


	class Builder {
		private var accessToken: String? = null
		private var contentType: ContentType? = null

		enum class ContentType(val detail: String) {
			Json("application/json");
		}

		fun accessToken(accessToken: String): Builder {
			this.accessToken = accessToken
			return this
		}

		fun contentType(contentType: ContentType): Builder {
			this.contentType = contentType
			return this
		}

		fun build(): Header {
			accessToken ?: throw SpotifyQueryException("Access Token for header must be supplied")

			return Header(accessToken!!, contentType?.detail)
		}
	}
}