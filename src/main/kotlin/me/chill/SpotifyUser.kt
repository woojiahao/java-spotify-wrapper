package me.chill

import me.chill.authentication.SpotifyAuthenticationHelper

class SpotifyUser(helper: SpotifyAuthenticationHelper) {
	var accessToken: String? = null
	var refreshToken: String? = null
	var expiryDuration: Int? = null
}