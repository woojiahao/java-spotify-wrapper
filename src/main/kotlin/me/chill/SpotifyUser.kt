package me.chill

import khttp.post
import me.chill.exceptions.SpotifyAuthenticationException
import me.chill.queries.album.AlbumTrackQuery
import me.chill.queries.album.ManyAlbumQuery
import me.chill.queries.album.SingleAlbumQuery
import me.chill.queries.artist.*
import me.chill.queries.browse.*
import me.chill.queries.follow.*
import me.chill.queries.library.*
import java.util.*
import kotlin.concurrent.timerTask

// TODO: Handle caching with e-tags
class SpotifyUser(
	val clientId: String,
	val clientSecret: String?,
	private var accessToken: String,
	private val refreshToken: String? = null,
	private var expiryDuration: Int? = null) {

	private val refreshTaskTimer = Timer()

	init {
		refreshToken?.let { _ ->
			expiryDuration?.let { _ ->
				println("Auto refreshing access tokens")
				startTimer()
			}
		}
	}

	fun disableTimer() = refreshTaskTimer.cancel()

	fun startTimer() {
		refreshTaskTimer.purge()
		refreshTaskTimer.scheduleAtFixedRate(
			timerTask {
				clientSecret?.let { _ ->
					val base64EncodedAuthorization = String(Base64.getUrlEncoder().encode("$clientId:$clientSecret".toByteArray()))

					val response = post(
						"https://accounts.spotify.com/api/token",
						mapOf(
							"Content-Type" to "application/x-www-form-urlencoded",
							"Authorization" to "Basic $base64EncodedAuthorization"
						),
						data = mapOf(
							"grant_type" to "refresh_token",
							"refresh_token" to refreshToken
						)
					)

					if (response.statusCode >= 400) {
						cancel()
						throw SpotifyAuthenticationException(
							mapOf(
								"Error Code" to response.statusCode.toString(),
								"Error" to response.jsonObject.getString("error"),
								"Error Description" to response.jsonObject.getString("error_description")
							)
						)
					}

					accessToken = response.jsonObject.getString("access_token")
					expiryDuration = response.jsonObject.getInt("expires_in")

					expiryDuration ?: cancel()
				}
			}, (expiryDuration!! * 1000).toLong(), (expiryDuration!! * 1000).toLong()
		)
	}

	fun getAccessToken() = accessToken

	fun getSingleAlbum(albumId: String) = SingleAlbumQuery.Builder(albumId, accessToken)

	fun getAlbumTracks(albumId: String) = AlbumTrackQuery.Builder(albumId, accessToken)

	fun getManyAlbums() = ManyAlbumQuery.Builder(accessToken)

	fun getSingleArtist(artistId: String) = SingleArtistQuery.Builder(artistId, accessToken)

	fun getArtistAlbums(artistId: String) = ArtistAlbumQuery.Builder(artistId, accessToken)

	fun getArtistTopTracks(artistId: String) = ArtistTopTrackQuery.Builder(artistId, accessToken)

	fun getRelatedArtists(artistId: String) = RelatedArtistsQuery.Builder(artistId, accessToken)

	fun getManyArtists() = ManyArtistQuery.Builder(accessToken)

	fun getCategory(categoryId: String) = SingleCategoryQuery.Builder(categoryId, accessToken)

	fun getCategoryPlaylists(categoryId: String) = CategoryPlaylistsQuery.Builder(categoryId, accessToken)

	fun getCategoryList() = CategoryListQuery.Builder(accessToken)

	fun getFeaturedPlaylists() = FeaturedPlaylistsQuery.Builder(accessToken)

	fun getNewReleases() = NewReleasesQuery.Builder(accessToken)

	fun getSeedRecommendation() = SeedQuery.Builder(accessToken)

	fun getAvailableGenreSeeds() = AvailableGenreSeedsQuery.Builder(accessToken)

	fun isFollowingUserOrArtist(userType: UserType) = IsFollowingUserOrArtistQuery.Builder(accessToken, userType)

	fun areUsersFollowingPlaylist(playlistId: String) = AreUsersFollowingPlaylistQuery.Builder(playlistId, accessToken)

	fun followUserOrArtist() = FollowUserOrArtistQuery.Builder(accessToken)

	fun followPlaylist(playlistId: String) = FollowPlaylistQuery.Builder(playlistId, accessToken)

	fun getFollowedArtists() = FollowedArtistsQuery.Builder(accessToken)

	fun unfollowUserOrArtist(userType: UserType) = UnfollowUserOrArtistQuery.Builder(accessToken, userType)

	fun unfollowPlaylist(playlistId: String) = UnfollowPlaylistQuery.Builder(playlistId, accessToken)

	fun checkSavedAlbums() = CheckSavedAlbumsQuery.Builder(accessToken)

	fun checkSavedTracks() = CheckSavedTracksQuery.Builder(accessToken)

	fun getSavedAlbums() = RetrieveSavedAlbumsQuery.Builder(accessToken)

	fun getSavedTracks() = RetrieveSavedTracksQuery.Builder(accessToken)

	fun removeSavedAlbums() = RemoveSavedAlbumsQuery.Builder(accessToken)

	fun removeSavedTracks() = RemoveSavedTracksQuery.Builder(accessToken)

	fun saveAlbums() = SaveAlbumsQuery.Builder(accessToken)
}