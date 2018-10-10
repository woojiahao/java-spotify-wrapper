package me.chill.authentication

import me.chill.exceptions.SpotifyAuthenticationException
import org.apache.http.client.utils.URIBuilder
import java.net.URL
import java.net.URLEncoder

class SpotifyAuthorizationFlow(private val helper: SpotifyAuthenticationHelper) {
	init {
		helper.clientId ?: throw SpotifyAuthenticationException("Client ID must be set")
		helper.redirectUrl ?: throw SpotifyAuthenticationException("Redirect URL must be set and whitelisted")
	}

	fun generateLoginUrl(): URL {
		val uriBuilder = URIBuilder()
			.setScheme("https")
			.setHost("accounts.spotify.com")
			.setPath("/authorize")
			.setParameter("client_id", helper.clientId)
			.setParameter("redirect_uri", helper.redirectUrl)
			.setParameter("response_type", "code")

		helper.state?.let { uriBuilder.setParameter("state", it) }

		helper.scopes.takeIf { it.isNotEmpty() }?.distinct()?.apply {
			val scopes = URLEncoder.encode(this.asSequence().distinct().joinToString(" ") { it.scopeName }, "UTF-8")
			uriBuilder.setParameter("scope", scopes)
		}

		helper.showDialog.takeIf { it }?.apply { uriBuilder.setParameter("show_dialog", "true") }

		return uriBuilder.build().toURL()
	}
}