package me.chill

import khttp.post
import me.chill.exceptions.SpotifyAuthenticationException
import me.chill.models.RepeatState
import me.chill.queries.album.GetAlbumTracksQuery
import me.chill.queries.album.GetSeveralAlbumsQuery
import me.chill.queries.album.GetSingleAlbumQuery
import me.chill.queries.artist.*
import me.chill.queries.browse.*
import me.chill.queries.follow.*
import me.chill.queries.library.*
import me.chill.queries.personalization.GetUserTopArtistsQuery
import me.chill.queries.personalization.GetUserTopTracksQuery
import me.chill.queries.player.*
import java.util.*
import kotlin.concurrent.timerTask

// TODO: Handle caching with e-tags
// TODO: Create an observer system for whenever the access token gets refreshed
class SpotifyUser(
	val clientId: String,
	val clientSecret: String?,
	private var accessToken: String,
	private val refreshToken: String? = null,
	private var expiryDuration: Int? = null) {

	private val refreshTaskTimer = Timer()

	constructor(accessToken: String) : this("", null, accessToken, null, null)

	init {
		refreshToken?.let { _ ->
			expiryDuration?.let { _ ->
				println("Auto refreshing access tokens")
				startTimer()
			}
		} ?: disableTimer()
	}

	fun disableTimer() = refreshTaskTimer.cancel()

	// TODO: When launching this method after disabling, an exception is thrown since the timer is cancelled, re-create a new timer and start that one instead
	fun startTimer() {
		refreshToken ?: throw SpotifyAuthenticationException("Unable to start timer when no refresh token is given")
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

	fun getExpiryDuration() = expiryDuration

	fun getSingleAlbum(albumId: String) = GetSingleAlbumQuery.Builder(albumId, accessToken)

	fun getAlbumTracks(albumId: String) = GetAlbumTracksQuery.Builder(albumId, accessToken)

	fun getSeveralAlbums() = GetSeveralAlbumsQuery.Builder(accessToken)

	fun getSingleArtist(artistId: String) = GetSingleArtistQuery.Builder(artistId, accessToken)

	fun getArtistAlbums(artistId: String) = GetArtistAlbumsQuery.Builder(artistId, accessToken)

	fun getArtistTopTracks(artistId: String) = GetArtistTopTracksQuery.Builder(artistId, accessToken)

	fun getRelatedArtists(artistId: String) = GetRelatedArtistsQuery.Builder(artistId, accessToken)

	fun getSeveralArtists() = GetSeveralArtistsQuery.Builder(accessToken)

	fun getCategory(categoryId: String) = GetSingleCategoryQuery.Builder(categoryId, accessToken)

	fun getCategoryPlaylists(categoryId: String) = GetCategoryPlaylistsQuery.Builder(categoryId, accessToken)

	fun getCategoryList() = GetCategoryListQuery.Builder(accessToken)

	fun getFeaturedPlaylists() = GetFeaturedPlaylistsQuery.Builder(accessToken)

	fun getNewReleases() = GetNewReleasesQuery.Builder(accessToken)

	fun getRecommendationsFromSeed() = GetRecommendationFromSeedQuery.Builder(accessToken)

	fun getAvailableGenreSeeds() = GetAvailableGenreSeedsQuery.Builder(accessToken)

	fun isFollowingUserOrArtist(userType: UserType) = IsFollowingUserOrArtistQuery.Builder(accessToken, userType)

	fun areUsersFollowingPlaylist(playlistId: String) = AreUsersFollowingPlaylistQuery.Builder(playlistId, accessToken)

	fun followUserOrArtist() = FollowUserOrArtistQuery.Builder(accessToken)

	fun followPlaylist(playlistId: String) = FollowPlaylistQuery.Builder(playlistId, accessToken)

	fun getFollowedArtists() = GetFollowedArtistsQuery.Builder(accessToken)

	fun unfollowUserOrArtist(userType: UserType) = UnfollowUserOrArtistQuery.Builder(accessToken, userType)

	fun unfollowPlaylist(playlistId: String) = UnfollowPlaylistQuery.Builder(playlistId, accessToken)

	fun checkSavedAlbums() = CheckSavedAlbumsQuery.Builder(accessToken)

	fun checkSavedTracks() = CheckSavedTracksQuery.Builder(accessToken)

	fun getSavedAlbums() = GetSavedAlbumsQuery.Builder(accessToken)

	fun getSavedTracks() = GetSavedTracksQuery.Builder(accessToken)

	fun removeSavedAlbums() = RemoveSavedAlbumsQuery.Builder(accessToken)

	fun removeSavedTracks() = RemoveSavedTracksQuery.Builder(accessToken)

	fun saveAlbums() = SaveAlbumsQuery.Builder(accessToken)

	fun saveTracks() = SaveTracksQuery.Builder(accessToken)

	fun getTopArtists() = GetUserTopArtistsQuery.Builder(accessToken)

	fun getTopTracks() = GetUserTopTracksQuery.Builder(accessToken)

	fun getAvailableDevices() = GetAvailableDevicesQuery.Builder(accessToken)

	fun getCurrentPlaybackInformation() = GetCurrentPlaybackInformationQuery.Builder(accessToken)

	fun getRecentlyPlayedTracks() = GetRecentlyPlayedTracksQuery.Builder(accessToken)

	fun getCurrentlyPlayingTrack() = GetCurrentlyPlayingTrackQuery.Builder(accessToken)

	fun pauseTrack() = PauseTrackQuery.Builder(accessToken)

	fun seekTrack() = SeekTrackQuery.Builder(accessToken)

	fun setRepeatMode(state: RepeatState) = SetRepeatModeQuery.Builder(accessToken, state)

	fun setVolume(volume: Int) = SetVolumeQuery.Builder(accessToken, volume)

	fun nextTrack() = NextTrackQuery.Builder(accessToken)

	fun previousTrack() = PreviousTrackQuery.Builder(accessToken)

	fun playTrack() = PlayTrackQuery.Builder(accessToken)

	/**
	 * state:
	 *
	 * 	true 	-> Shuffle
	 *
	 * 	false -> Don't shuffle
	 */
	fun toggleShuffle(state: Boolean) = ToggleShuffleQuery.Builder(accessToken, state)
}