package me.chill.authentication

import me.chill.exceptions.SpotifyAuthenticationException
import okhttp3.HttpUrl
import org.junit.Test
import java.net.URLEncoder
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.assertEquals

class SpotifyAuthorizationFlowAuthorizationLinkTest {
  private val clientId = "client_id"
  private val clientSecret = "client_secret"
  private val redirectUrl = "https://woojiahao.github.io"
  private lateinit var bareHelper: SpotifyAuthenticationHelper.Builder

  @BeforeTest
  fun beforeTest() {
    bareHelper = SpotifyAuthenticationHelper.Builder()
      .clientId(clientId)
      .clientSecret(clientSecret)
      .redirectUrl(redirectUrl)
  }

  @Test(expected = SpotifyAuthenticationException::class)
  fun missing_client_id_throws_exception() {
    val helper = SpotifyAuthenticationHelper.Builder()
      .redirectUrl(redirectUrl)
      .build()

    SpotifyAuthorizationFlow(helper)
  }

  @Test(expected = SpotifyAuthenticationException::class)
  fun missing_redirect_url_throws_exception() {
    val helper = SpotifyAuthenticationHelper.Builder()
      .clientId(clientId)
      .build()

    SpotifyAuthorizationFlow(helper)
  }

  @Test(expected = SpotifyAuthenticationException::class)
  fun missing_client_secret_throws_exception() {
    val helper = SpotifyAuthenticationHelper.Builder()
      .clientId(clientId)
      .redirectUrl(redirectUrl)
      .build()

    SpotifyAuthorizationFlow(helper)
  }

  @Test
  fun bare_url_should_be_formatted_properly() {
    testUrl(bareHelper.build())
  }

  @Test
  fun show_dialog_should_add_parameter_to_url() {
    testUrl(bareHelper.showDialog(true).build(), mapOf("show_dialog" to "true"))
  }

  @Test
  fun scopes_should_add_parameter_to_url() {
    val scopes = arrayOf(
      SpotifyScope.FollowRead,
      SpotifyScope.AccountDetailsRead
    )
    scopes.forEach { bareHelper.addScope(it) }
    testUrl(
      bareHelper.build(),
      mapOf("scope" to scopes.asSequence().distinct().joinToString(" ") { it.scopeName })
    )
  }

  @Test
  fun duplicate_scopes_should_not_duplicate_in_url() {
    val scopes = listOf(
      SpotifyScope.BirthDateRead,
      SpotifyScope.AccountDetailsRead,
      SpotifyScope.FollowModify,
      SpotifyScope.FollowModify
    )

    val scopesInUrl = scopes.asSequence().distinct().joinToString(" ") { it.scopeName }
    testUrl(bareHelper.setScopes(scopes).build(), mapOf("scope" to scopesInUrl))
  }

  private fun testUrl(helper: SpotifyAuthenticationHelper, expectedArguments: Map<String, String> = emptyMap()) {
    val builder = HttpUrl.Builder()
      .scheme("https")
      .host("accounts.spotify.com")
      .addPathSegment("authorize")
      .addQueryParameter("client_id", clientId)
      .addQueryParameter("redirect_uri", redirectUrl)
      .addQueryParameter("response_type", "code")
    expectedArguments.forEach { key, value -> builder.addQueryParameter(key, value) }

    val flow = SpotifyAuthorizationFlow(helper)
    assertEquals(builder.build().url().toString(), flow.generateAuthorizationUrl().toString())
  }
}