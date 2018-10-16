package me.chill

import me.chill.authentication.SpotifyAuthenticationHelper
import me.chill.queries.album.AlbumTrackQuery
import me.chill.queries.album.ManyAlbumQuery
import me.chill.queries.album.SingleAlbumQuery
import me.chill.queries.artist.*

class SpotifyUser(val helper: SpotifyAuthenticationHelper, val accessToken: String) {
	var refreshToken: String? = null
	var expiryDuration: Int? = null

	init {
		refreshToken?.let {
			// TODO: Implement token refreshing logic
		}
	}

	fun getSingleAlbum(albumId: String) = SingleAlbumQuery.Builder(albumId, accessToken)

	fun getTracks(albumId: String) = AlbumTrackQuery.Builder(albumId, accessToken)

	fun getManyAlbums() = ManyAlbumQuery.Builder(accessToken)

	fun getSingleArtist(artistId: String) = SingleArtistQuery.Builder(artistId, accessToken)

	fun getArtistAlbums(artistId: String) = ArtistAlbumQuery.Builder(artistId, accessToken)

	fun getArtistTopTracks(artistId: String) = ArtistTopTrackQuery.Builder(artistId, accessToken)

	fun getRelatedArtists(artistId: String) = RelatedArtistsQuery.Builder(artistId, accessToken)

	fun getManyArtists() = ManyArtistQuery.Builder(accessToken)
}