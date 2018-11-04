package me.chill.authentication

import me.chill.exceptions.SpotifyAuthenticationException
import okhttp3.HttpUrl
import java.net.MalformedURLException

class SpotifyImplicitGrantFlow(
  private val helper: SpotifyAuthenticationHelper) : SpotifyAuthenticationMethod(helper, false, true) {

  enum class ParseComponent { Expiry, Token, Error, State }

  @Throws(MalformedURLException::class, SpotifyAuthenticationException::class)
  fun extractAuthorizationInfo(url: String): Map<SpotifyAuthenticationComponent, String> {
    val httpUrl = HttpUrl.parse(url) ?: throw MalformedURLException("URL: $url is malformed")

    val error = httpUrl.queryParameter("error")

    error?.let {
      throw SpotifyAuthenticationException(
        ParseComponent.Error,
        mapOf(
          "Cause" to "Error occurred with user authentication",
          "Error Message" to it
        )
      )
    }

    var accessToken: String? = null
    var expiryDuration: String? = null

    httpUrl.fragment()?.let {
      val newUrl = HttpUrl.parse("$redirectUrl?$it") ?: throw MalformedURLException("URL: $url is malformed")
      accessToken = newUrl.queryParameter("access_token")
      expiryDuration = newUrl.queryParameter("expires_in")

      val state = newUrl.queryParameter("state")
      checkMatchingState(state)
    }

    accessToken ?: throw SpotifyAuthenticationException(
      ParseComponent.Token,
      mapOf("Cause" to "Access token is empty")
    )

    expiryDuration ?: throw SpotifyAuthenticationException(
      ParseComponent.Expiry,
      mapOf("Cause" to "Expiry duration is empty")
    )

    return mapOf(
      SpotifyAuthenticationComponent.AccessToken to accessToken!!,
      SpotifyAuthenticationComponent.ExpiryDuration to expiryDuration!!
    )
  }

  fun generateAuthorizationUrl() = super.generateAuthorizationUrl("token")
}