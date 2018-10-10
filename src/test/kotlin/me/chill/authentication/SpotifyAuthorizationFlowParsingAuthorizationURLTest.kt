package me.chill.authentication

import me.chill.exceptions.SpotifyAuthenticationException
import org.junit.Test
import java.net.MalformedURLException
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SpotifyAuthorizationFlowParsingAuthorizationURLTest {
	private val clientId = "cea6a21eeb874d1d91dbaaccce0996f3"
	private val redirectUrl = "https://woojiahao.github.io"

	private val bareHelper = SpotifyAuthenticationHelper.Builder()
		.setClientId("cea6a21eeb874d1d91dbaaccce0996f3")
		.setRedirectUrl("https://woojiahao.github.io")
		.build()

	private val flow = SpotifyAuthorizationFlow(bareHelper)

	@Test(expected = MalformedURLException::class)
	fun malformed_redirect_url_throws_malformed_url_exception() {
		flow.isRedirectedUrl("skldjfhkhlk://.asdfafas.asdf/home")
	}

	@Test
	fun url_not_starting_with_redirect_url_fails() {
		assertFalse(flow.isRedirectedUrl("https://google.com/?code=234df32cv23n4j"))
	}

	@Test
	fun url_starting_with_redirect_url_passes() {
		assertTrue(flow.isRedirectedUrl("https://woojiahao.github.io/?state=234df32cv23n4j"))
	}

	@Test(expected = SpotifyAuthenticationException::class)
	fun user_denying_authorization_throws_exception() {
		flow.parseAuthorizationUrl("https://woojiahao.github.io/?error=access_denied")
	}

	@Test(expected = SpotifyAuthenticationException::class)
	fun passing_authorization_but_no_code_throws_exception() {
		flow.parseAuthorizationUrl("https://woojiahao.github.io/")
	}

	@Test(expected = SpotifyAuthenticationException::class)
	fun receiving_state_must_match_sent_state() {
		val helper = SpotifyAuthenticationHelper.Builder()
			.setClientId(clientId)
			.setRedirectUrl(redirectUrl)
			.setState("apple")
			.build()
		val customFlow = SpotifyAuthorizationFlow(helper)
		customFlow.parseAuthorizationUrl("https://woojiahao.github.io/?state=pear")
	}

	@Test(expected = SpotifyAuthenticationException::class)
	fun sent_state_must_receive_state() {
		val helper = SpotifyAuthenticationHelper.Builder()
			.setClientId(clientId)
			.setRedirectUrl(redirectUrl)
			.setState("apple")
			.build()
		val customFlow = SpotifyAuthorizationFlow(helper)
		customFlow.parseAuthorizationUrl("https://woojiahao.github.io/")
	}

	@Test
	fun receiving_state_matches_sent_state() {
		val helper = SpotifyAuthenticationHelper.Builder()
			.setClientId(clientId)
			.setRedirectUrl(redirectUrl)
			.setState("apple")
			.build()
		val customFlow = SpotifyAuthorizationFlow(helper)
		val parsed = customFlow.parseAuthorizationUrl("https://woojiahao.github.io/?state=apple&code=234df32cv23n4j")
		assertEquals("apple", parsed[SpotifyAuthorizationFlow.Component.State])
	}

	@Test
	fun code_should_be_extracted_whole_from_url() {
		assertEquals("2u4y3vh23vg", flow.extractCode("https://woojiahao.github.io/?code=2u4y3vh23vg"))
	}
}