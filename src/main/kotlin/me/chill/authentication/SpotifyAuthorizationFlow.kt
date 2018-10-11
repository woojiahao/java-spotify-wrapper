package me.chill.authentication

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.chill.SpotifyUser
import me.chill.exceptions.SpotifyAuthenticationException
import okhttp3.*
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class SpotifyAuthorizationFlow(private val helper: SpotifyAuthenticationHelper) {

	private var clientId = ""
	private var redirectUrl = ""
	private var clientSecret = ""

	enum class ParseComponent { State, Code, Error }

	enum class ExchangeComponent { AccessToken, RefreshToken, ExpiryDuration }

	init {
		helper.clientId ?: throw SpotifyAuthenticationException("Client ID must be set")
		helper.redirectUrl ?: throw SpotifyAuthenticationException("Redirect URL must be set")
		helper.clientSecret ?: throw SpotifyAuthenticationException("Client Secret must be set")

		clientId = helper.clientId
		redirectUrl = helper.redirectUrl
		clientSecret = helper.clientSecret
	}

	fun generateSpotifyUser(exchangeComponentMap: Map<ExchangeComponent, String>): SpotifyUser {
		val accessToken = exchangeComponentMap[ExchangeComponent.AccessToken]
		val refreshToken = exchangeComponentMap[ExchangeComponent.RefreshToken]
		val expiryDuration = exchangeComponentMap[ExchangeComponent.ExpiryDuration]

		val user = SpotifyUser(helper, accessToken!!)
		refreshToken?.let { user.refreshToken = it }
		expiryDuration?.toIntOrNull()?.let { user.expiryDuration = it }

		return user
	}

	@Throws(SpotifyAuthenticationException::class)
	fun exchangeAuthorizationCode(authorizationCode: String): Map<ExchangeComponent, String> {
		val accessTokenUrl = HttpUrl.Builder()
			.scheme("https")
			.host("accounts.spotify.com")
			.addPathSegment("api")
			.addPathSegment("token")
			.build()
			.url()

		val requestBody = FormBody.Builder()
			.add("grant_type", "authorization_code")
			.add("code", authorizationCode)
			.add("redirect_uri", redirectUrl)
			.add("client_id", clientId)
			.add("client_secret", clientSecret)
			.build()

		val headers = Headers.Builder()
			.add("Content-Type", "application/x-www-form-urlencoded")
			.build()

		val request = Request.Builder()
			.url(accessTokenUrl)
			.headers(headers)
			.post(requestBody)
			.build()

		val client = OkHttpClient()
		var response: Response? = null
		try {
			response = client.newCall(request).execute()
		} catch (e: IOException) {
			e.printStackTrace()
		}

		response ?: throw SpotifyAuthenticationException("JSON response for token was null")

		val accessTokenJson = Gson().fromJson(response.body()?.string(), JsonObject::class.java)

		if (!response.isSuccessful) {
			throw SpotifyAuthenticationException(
				mapOf(
					"Cause" to "Error when retrieving access token",
					"Error Code" to response.code().toString(),
					"Error" to accessTokenJson["error"].asString,
					"Error Description" to accessTokenJson["error_description"].asString
				)
			)
		}


		return mapOf(
			ExchangeComponent.AccessToken to accessTokenJson["access_token"].asString,
			ExchangeComponent.RefreshToken to accessTokenJson["refresh_token"].asString,
			ExchangeComponent.ExpiryDuration to accessTokenJson["expires_in"].asString
		)
	}

	@Throws(SpotifyAuthenticationException::class)
	fun parseAuthorizationUrl(url: String): Map<ParseComponent, String?> {
		val httpUrl = HttpUrl.parse(url) ?: return emptyMap()

		val error = httpUrl.queryParameter("error")
		val code = httpUrl.queryParameter("code")
		val state = httpUrl.queryParameter("state")

		helper.state?.let { sentState ->
			state ?: throw SpotifyAuthenticationException(
				ParseComponent.State,
				mapOf(
					"Cause" to "state was not received despite state being sent",
					"Sent State" to sentState
				)
			)

			if (sentState != state) {
				throw SpotifyAuthenticationException(
					ParseComponent.State,
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
				ParseComponent.Error,
				mapOf(
					"Cause" to "Error occurred with user authentication",
					"Error Message" to it
				)
			)
		}

		error ?: code
		?: throw SpotifyAuthenticationException(
			ParseComponent.Code,
			mapOf("Cause" to "No authorization code is provided despite the authorization " +
				"process being successful")
		)

		return mapOf(
			ParseComponent.State to state,
			ParseComponent.Error to error,
			ParseComponent.Code to code
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