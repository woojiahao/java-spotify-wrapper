package me.chill.authentication

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.chill.SpotifyUser
import okhttp3.*
import java.io.IOException
import java.util.*

class SpotifyClientCredentialFlow(private val helper: SpotifyAuthenticationHelper) {
	private var clientId: String
	private var clientSecret: String

	enum class AuthenticationComponent { AccessToken, ExpiryDuration }

	init {
		helper.clientId ?: throw SpotifyAuthenticationException("Client ID must be set")
		helper.clientSecret ?: throw SpotifyAuthenticationException("Client secret must be set")

		clientId = helper.clientId
		clientSecret = helper.clientSecret
	}

	fun generateSpotifyUser(authenticationMap: Map<AuthenticationComponent, String>): SpotifyUser {
		val accessToken = authenticationMap[AuthenticationComponent.AccessToken]
		val expiryDuration = authenticationMap[AuthenticationComponent.ExpiryDuration]

		val user = SpotifyUser(helper, accessToken!!)
		expiryDuration?.toIntOrNull()?.let { user.expiryDuration = it }

		return user
	}

	@Throws(SpotifyAuthenticationException::class)
	fun requestAuthentication(): Map<AuthenticationComponent, String> {
		val base64EncodedAuthorization = String(Base64.getUrlEncoder().encode("$clientId:$clientSecret".toByteArray()))

		val accessTokenUrl = HttpUrl.Builder()
			.scheme("https")
			.host("accounts.spotify.com")
			.addPathSegment("api")
			.addPathSegment("token")
			.build()
			.url()

		val requestBody = FormBody.Builder().add("grant_type", "client_credentials").build()

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
					"Cause" to "Error when retrieving access token",
					"Error Code" to response.code().toString(),
					"Error" to accessTokenJson["error"].asString,
					"Error Description" to accessTokenJson["error_description"].asString
				)
			)
		}

		return mapOf(
			AuthenticationComponent.AccessToken to accessTokenJson["access_token"].asString,
			AuthenticationComponent.ExpiryDuration to accessTokenJson["expires_in"].asString
		)
	}
}