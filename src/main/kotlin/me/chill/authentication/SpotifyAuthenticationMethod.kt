package me.chill.authentication

import me.chill.SpotifyUser
import okhttp3.HttpUrl
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

open class SpotifyAuthenticationMethod(
	private val helper: SpotifyAuthenticationHelper,
	private val isClientSecretMandatory: Boolean = false,
	private val isRedirectUrlMandatory: Boolean = false) {

	protected var clientId = ""
	protected var clientSecret = ""
	protected var redirectUrl = ""

	init {
		helper.clientId ?: throw SpotifyAuthenticationException("Client ID must be set")
		clientId = helper.clientId

		if (isClientSecretMandatory) {
			helper.clientSecret ?: throw SpotifyAuthenticationException("Client secret must be set")
			clientSecret = helper.clientSecret
		}

		if (isRedirectUrlMandatory) {
			helper.redirectUrl ?: throw SpotifyAuthenticationException("Redirect URL must be set")
			redirectUrl = helper.redirectUrl
		}
	}

	fun generateSpotifyUser(authenticationMap: Map<SpotifyAuthenticationComponent, String>): SpotifyUser {
		val accessToken = authenticationMap[SpotifyAuthenticationComponent.AccessToken]
		val expiryDuration = authenticationMap[SpotifyAuthenticationComponent.ExpiryDuration]

		val user = SpotifyUser(helper, accessToken!!)
		expiryDuration?.toIntOrNull()?.let { user.expiryDuration = it }

		authenticationMap
			.takeIf { it.containsKey(SpotifyAuthenticationComponent.RefreshToken) }
			?.get(SpotifyAuthenticationComponent.RefreshToken)
			.let { user.refreshToken = it }

		return user
	}

	fun isFinalRedirectUrl(url: String): Boolean {
		val httpUrl = HttpUrl.parse(url) ?: throw MalformedURLException("URL: $url is malformed")
		helper.redirectUrl ?: throw IllegalStateException("Redirect URL should have been specified previously")
		return httpUrl.toString().startsWith(helper.redirectUrl) && httpUrl.queryParameter("state") != null
	}

	protected fun checkMatchingState(receivedState: String?) {
		helper.state?.let { sentState ->
			receivedState ?: throw SpotifyAuthenticationException(
				SpotifyImplicitGrantFlow.ParseComponent.State,
				mapOf(
					"Cause" to "state was not received despite state being sent",
					"Sent State" to sentState
				)
			)

			if (sentState != receivedState) {
				throw SpotifyAuthenticationException(
					SpotifyImplicitGrantFlow.ParseComponent.State,
					mapOf(
						"Cause" to "state received does not correspond with the sent state",
						"Sent State" to sentState,
						"Received State" to receivedState
					)
				)
			}
		}
	}

	open fun generateAuthorizationUrl(responseType: String = ""): URL {
		val builder = HttpUrl.Builder()
			.scheme("https")
			.host("accounts.spotify.com")
			.addPathSegment("authorize")
			.addQueryParameter("client_id", helper.clientId)
			.addQueryParameter("redirect_uri", helper.redirectUrl)
			.addQueryParameter("response_type", responseType)

		helper.state?.let { builder.addQueryParameter("state", it) }

		helper.scopes.takeIf { it.isNotEmpty() }?.distinct()?.apply {
			val scopes = URLEncoder.encode(this.asSequence().distinct().joinToString(" ") { it.scopeName }, "UTF-8")
			builder.addQueryParameter("scope", scopes)
		}

		helper.showDialog.takeIf { it }?.apply { builder.addQueryParameter("show_dialog", "true") }

		return builder.build().url()
	}
}