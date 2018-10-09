package me.chill

import me.chill.authentication.generateLoginPageUrl
import me.chill.exceptions.SpotifyAuthenticationException
import java.awt.Desktop
import java.net.URI

class SpotifyUser private constructor(private val clientId: String, private val redirectUrl: String) {
	var loginUrl: String private set

	init {
		loginUrl = generateLoginPageUrl(clientId, redirectUrl)
	}

	class Builder {
		private var clientId: String? = null
		private var redirectUrl: String? = null

		fun setClientId(token: String): Builder {
			this.clientId = token
			return this
		}

		fun setRedirectUrl(url: String): Builder {
			this.redirectUrl = url
			return this
		}

		fun build(): SpotifyUser {
			clientId ?: throw SpotifyAuthenticationException(mapOf("Cause" to "Client ID has not been configured"))
			redirectUrl ?: throw SpotifyAuthenticationException(mapOf("Cause" to "Redirect URL has not been configured"))

			return SpotifyUser(clientId!!, redirectUrl!!)
		}
	}

	fun launchLoginPage() {
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(URI.create(loginUrl))
		} else {
			throw SpotifyAuthenticationException(mapOf("Cause" to "Unable to launch the default browser on the machine"))
		}
	}
}