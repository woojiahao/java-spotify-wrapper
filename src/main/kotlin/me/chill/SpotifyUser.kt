package me.chill

import com.neovisionaries.i18n.CountryCode
import me.chill.authentication.SpotifyAuthenticationHelper
import me.chill.queries.album.AlbumTrackQuery
import me.chill.queries.album.ManyAlbumQuery
import me.chill.queries.album.SingleAlbumQuery
import me.chill.queries.album.SpotifyAlbumQuery

class SpotifyUser(val helper: SpotifyAuthenticationHelper, val accessToken: String) {
	var refreshToken: String? = null
	var expiryDuration: Int? = null

	init {
		refreshToken?.let {
			// TODO: Implement token refreshing logic
		}
	}

	fun getSingleAlbum(id: String) = SingleAlbumQuery.Builder(id, accessToken)

	fun getTracks(id: String) = AlbumTrackQuery.Builder(id, accessToken)

	fun getManyAlbums() = ManyAlbumQuery.Builder(accessToken)
}