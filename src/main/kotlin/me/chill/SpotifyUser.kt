package me.chill

import com.neovisionaries.i18n.CountryCode
import me.chill.authentication.SpotifyAuthenticationHelper
import me.chill.queries.SpotifyAlbumQuery

class SpotifyUser(val helper: SpotifyAuthenticationHelper, val accessToken: String) {
	var refreshToken: String? = null
	var expiryDuration: Int? = null

	init {
		refreshToken?.let {
			// TODO: Implement token refreshing logic
		}
	}

	fun getAlbum(id: String, countryCode: CountryCode? = null) = SpotifyAlbumQuery().getAlbum(accessToken, id, countryCode)
}