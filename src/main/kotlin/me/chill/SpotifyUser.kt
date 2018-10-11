package me.chill

import me.chill.authentication.SpotifyAuthenticationHelper

class SpotifyUser(val helper: SpotifyAuthenticationHelper, val accessToken: String) {
	var refreshToken: String? = null
	var expiryDuration: Int? = null
}