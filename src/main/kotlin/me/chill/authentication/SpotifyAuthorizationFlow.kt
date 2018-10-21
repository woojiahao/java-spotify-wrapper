package me.chill.authentication

import com.google.gson.Gson
import com.google.gson.JsonObject
import khttp.post
import okhttp3.HttpUrl
import java.net.MalformedURLException
import java.util.*

class SpotifyAuthorizationFlow(
	private val helper: SpotifyAuthenticationHelper) : SpotifyAuthenticationMethod(helper, true, true) {

	enum class ParseComponent { State, Code, Error }

	@Throws(SpotifyAuthenticationException::class)
	fun exchangeAuthorizationCode(authorizationCode: String): Map<SpotifyAuthenticationComponent, String> {
		val base64EncodedAuthorization = String(Base64.getUrlEncoder().encode("$clientId:$clientSecret".toByteArray()))

		val response = post(
			"https://accounts.spotify.com/api/token",
			mapOf(
				"Content-Type" to "application/x-www-form-urlencoded",
				"Authorization" to "Basic $base64EncodedAuthorization"
			),
			data = mapOf(
				"grant_type" to "authorization_code",
				"code" to authorizationCode,
				"redirect_uri" to redirectUrl
			))

		val accessTokenJson = Gson().fromJson(response.text, JsonObject::class.java)

		if (response.statusCode >= 400) {
			throw SpotifyAuthenticationException(
				mapOf(
					"Cause" to "Error when retrieving models token",
					"Error Code" to response.statusCode.toString(),
					"Error" to accessTokenJson["error"].asString,
					"Error Description" to accessTokenJson["error_description"].asString
				)
			)
		}

		return mapOf(
			SpotifyAuthenticationComponent.AccessToken to accessTokenJson["access_token"].asString,
			SpotifyAuthenticationComponent.RefreshToken to accessTokenJson["refresh_token"].asString,
			SpotifyAuthenticationComponent.ExpiryDuration to accessTokenJson["expires_in"].asString
		)
	}

	@Throws(SpotifyAuthenticationException::class)
	fun parseAuthorizationUrl(url: String): Map<ParseComponent, String?> {
		val httpUrl = HttpUrl.parse(url) ?: throw MalformedURLException("URL: $url is malformed")

		val error = httpUrl.queryParameter("error")
		val code = httpUrl.queryParameter("code")
		val state = httpUrl.queryParameter("state")

		error?.let {
			throw SpotifyAuthenticationException(
				ParseComponent.Error,
				mapOf(
					"Cause" to "Error occurred with user authentication",
					"Error Message" to it
				)
			)
		}

		checkMatchingState(state)

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

	fun generateAuthorizationUrl() = super.generateAuthorizationUrl("code")
}