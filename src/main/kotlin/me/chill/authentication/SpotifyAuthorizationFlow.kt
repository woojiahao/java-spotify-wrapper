package me.chill.authentication

import me.chill.exceptions.SpotifyAuthenticationException
import okhttp3.HttpUrl
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class SpotifyAuthorizationFlow(private val helper: SpotifyAuthenticationHelper) {
	private var clientId = ""
	private var redirectUrl = ""

	enum class Component {
		State, Code, Error;
	}

	init {
		helper.clientId ?: throw SpotifyAuthenticationException("Client ID must be set")
		helper.redirectUrl ?: throw SpotifyAuthenticationException("Redirect URL must be set")

		clientId = helper.clientId
		redirectUrl = helper.redirectUrl
	}

	fun parseAuthorizationUrl(url: String): Map<Component, String?> {
		val httpUrl = HttpUrl.parse(url) ?: return emptyMap()

		val error = httpUrl.queryParameter("error")
		val code = httpUrl.queryParameter("code")
		val state = httpUrl.queryParameter("state")

		helper.state?.let { sentState ->
			state ?: throw SpotifyAuthenticationException(
				mapOf(
					"Cause" to "state was not received despite state being sent",
					"Sent State" to sentState
				)
			)

			if (sentState != state) {
				throw SpotifyAuthenticationException(
					mapOf(
						"Cause" to "state received does not correspond with the sent state",
						"Sent State" to sentState,
						"Received State" to state
					)
				)
			}
		}

		error?.let {
			throw SpotifyAuthenticationException(
				mapOf(
					"Cause" to "Error occurred with user authentication",
					"Error Message" to it
				)
			)
		}

		error ?: code
		?: throw SpotifyAuthenticationException("No authorization code is provided despite the authorization " +
			"process being successful")

		return mapOf(
			Component.State to state,
			Component.Error to error,
			Component.Code to code
		)
	}

	fun isRedirectedUrl(url: String): Boolean {
		val httpUrl = HttpUrl.parse(url) ?: throw MalformedURLException("URL: $url is malformed")
		return httpUrl.toString().startsWith(redirectUrl) && httpUrl.queryParameter("state") != null
	}

	fun generateLoginUrl(): URL {
		val builder = HttpUrl.Builder()
			.scheme("https")
			.host("accounts.spotify.com")
			.addPathSegment("authorize")
			.addQueryParameter("client_id", clientId)
			.addQueryParameter("redirect_uri", redirectUrl)
			.addQueryParameter("response_type", "code")

		helper.state?.let { builder.addQueryParameter("state", it) }

		helper.scopes.takeIf { it.isNotEmpty() }?.distinct()?.apply {
			val scopes = URLEncoder.encode(this.asSequence().distinct().joinToString(" ") { it.scopeName }, "UTF-8")
			builder.addQueryParameter("scope", scopes)
		}

		helper.showDialog.takeIf { it }?.apply { builder.addQueryParameter("show_dialog", "true") }

		return builder.build().url()
	}
}