package me.chill.authentication

import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.*
import java.io.IOException
import java.net.MalformedURLException
import java.util.*

class SpotifyAuthorizationFlow(
	private val helper: SpotifyAuthenticationHelper) : SpotifyAuthenticationMethod(helper, true, true) {

	enum class ParseComponent { State, Code, Error }

	@Throws(SpotifyAuthenticationException::class)
	fun exchangeAuthorizationCode(authorizationCode: String): Map<SpotifyAuthenticationComponent, String> {
		val base64EncodedAuthorization = String(Base64.getUrlEncoder().encode("$clientId:$clientSecret".toByteArray()))

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
			.add("Authorization", "Basic $base64EncodedAuthorization")
			.build()

		val request = Request.Builder().url(accessTokenUrl).headers(headers).post(requestBody).build()

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
					"Cause" to "Error when retrieving models token",
					"Error Code" to response.code().toString(),
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