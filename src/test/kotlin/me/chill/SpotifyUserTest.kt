package me.chill

import me.chill.exceptions.SpotifyAuthenticationException
import org.junit.Test
import java.net.URLEncoder
import kotlin.test.assertEquals

class SpotifyUserTest {
	private val sampleClientId = "cea6a21eeb874d1d91dbaaccce0996f3"
	private val sampleRedirectUrl = "https://woojiahao.github.io"

	@Test(expected = SpotifyAuthenticationException::class)
	fun client_id_must_be_set() {
		SpotifyUser.Builder().build()
	}

	@Test(expected = SpotifyAuthenticationException::class)
	fun redirect_url_must_be_set() {
		SpotifyUser.Builder().setClientId(sampleClientId).build()
	}

	@Test
	fun default_user_has_login_url() {
		val user = SpotifyUser.Builder().setClientId(sampleClientId).setRedirectUrl(sampleRedirectUrl).build()

		val intendedUrl = "https://accounts.spotify.com/authorize" +
			"?response_type=code" +
			"&client_id=$sampleClientId" +
			"&scope=user-read-private+user-read-email" +
			"&redirect_uri=${URLEncoder.encode(sampleRedirectUrl, "UTF-8")}"
		assertEquals(intendedUrl, user.loginUrl)
	}
}