package me.chill.authentication

import javafx.scene.effect.Light
import junit.framework.Assert.assertEquals
import me.chill.exceptions.SpotifyAuthenticationException
import org.apache.http.client.utils.URIBuilder
import org.junit.Test
import java.net.URLEncoder
import kotlin.test.BeforeTest

class SpotifyAuthorizationFlowTest {
	private val clientId = "cea6a21eeb874d1d91dbaaccce0996f3"
	private val redirectUrl = "https://woojiahao.github.io"
	private lateinit var bareHelper: SpotifyAuthenticationHelper.Builder

	@BeforeTest
	fun beforeTest() {
		bareHelper = SpotifyAuthenticationHelper.Builder().setClientId(clientId).setRedirectUrl(redirectUrl)
	}

	@Test(expected = SpotifyAuthenticationException::class)
	fun client_id_must_be_set_else_throw_exception() {
		val helper = SpotifyAuthenticationHelper.Builder()
			.setRedirectUrl(redirectUrl)
			.build()

		SpotifyAuthorizationFlow(helper)
	}

	@Test(expected = SpotifyAuthenticationException::class)
	fun redirect_url_must_be_set_else_throw_exception() {
		val helper = SpotifyAuthenticationHelper.Builder()
			.setClientId(clientId)
			.build()

		SpotifyAuthorizationFlow(helper)
	}

	@Test
	fun bare_url_should_be_formatted_properly() {
		testUrl(bareHelper.build())
	}

	@Test
	fun show_dialog_should_add_parameter_to_url() {
		testUrl(bareHelper.setShowDialog(true).build(), mapOf("show_dialog" to "true"))
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
			mapOf("scope" to URLEncoder.encode(scopes.asSequence().distinct().joinToString(" ") { it.scopeName }, "UTF-8"))
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

		val scopesInUrl = URLEncoder.encode(scopes.asSequence().distinct().joinToString(" ") { it.scopeName }, "UTF-8")
		testUrl(bareHelper.setScopes(scopes).build(), mapOf("scope" to scopesInUrl))
	}

	private fun testUrl(helper: SpotifyAuthenticationHelper, expectedArguments: Map<String, String> = emptyMap()) {
		val uriBuilder = URIBuilder()
			.setScheme("https")
			.setHost("accounts.spotify.com")
			.setPath("/authorize")
			.setParameter("client_id", helper.clientId)
			.setParameter("redirect_uri", helper.redirectUrl)
			.setParameter("response_type", "code")
		expectedArguments.forEach { key, value -> uriBuilder.setParameter(key, value) }

		val flow = SpotifyAuthorizationFlow(helper)
		assertEquals(uriBuilder.build().toURL().toString(), flow.generateLoginUrl().toString())
		println(flow.generateLoginUrl())
	}
}