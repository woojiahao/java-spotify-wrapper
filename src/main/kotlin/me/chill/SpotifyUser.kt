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
import me.chill.queries.playlist.*
import me.chill.queries.profiles.GetCurrentUserProfileQuery
import me.chill.queries.profiles.GetUserProfileQuery
import java.util.*
import kotlin.concurrent.timerTask
import me.chill.authentication.SpotifyAuthorizationFlow
import me.chill.authentication.SpotifyScope
import me.chill.queries.track.*

// TODO: Handle caching with e-tags
// TODO: Create an observer system for whenever the access token gets refreshed
/**
 * User object to interface with the API.
 *
 * **One-time User:**
 *
 * If no refresh token is needed, a SpotifyUser can be created from a non-refreshing access token via the secondary constructor:
 *
 * ```
 * SpotifyUser user = new SpotifyUser("<access_token>");
 * ```
 *
 * This can be used when attempting to access the API without a client.
 *
 * **Note:** Some endpoints are not available without a client's authorization via [SpotifyScope], these are noted in
 * the individual query class.
 *
 * **Refreshing User:**
 *
 * When creating a user object that refreshes the access token, use the primary constructor. However, it is recommended
 * to create an auto-refreshing SpotifyUser by using the [SpotifyAuthorizationFlow] class.
 *
 * Access token refreshing begins automatically once the user is created, auto-refreshing can be disabled using [disableTimer]
 * and re-enabled using [startTimer].
 *
 * @param clientId Client ID of the application.
 * @param clientSecret Client secret of the application.
 * @param accessToken Access token generated after the client authorizes the application.
 * @param refreshToken Refresh token for the client's access token, only accessible via the [SpotifyAuthorizationFlow] authentication method.
 * @param expiryDuration Expiry duration of the access token, only accessible via the [SpotifyAuthorizationFlow] authentication method.
 * @constructor Creates an auto-refreshing SpotifyUser.
 * @author Woo Jia Hao
 */
class SpotifyUser(
  private val clientId: String,
  private val clientSecret: String?,
  private var accessToken: String,
  private val refreshToken: String? = null,
  private var expiryDuration: Int? = null) {

  private val refreshTaskTimer = Timer()

  /**
   * @constructor Creates a non-refreshing SpotifyUser.
   * @param accessToken Access token generated after the client authorizes the application.
   */
  constructor(accessToken: String) : this("", null, accessToken, null, null)

  init {
    refreshToken?.let { _ ->
      expiryDuration?.let { _ ->
        println("Auto refreshing access tokens")
        startTimer()
      }
    } ?: disableTimer()
  }

  /**
   * Disables the refresh timer, the access token will no longer be refreshed.
   */
  fun disableTimer() = refreshTaskTimer.cancel()

  // TODO: When launching this method after disabling, an exception is thrown since the timer is cancelled, re-create a new timer and start that one instead
  /**
   * Starts the refresh timer, the access token will be refreshed in intervals specified by [expiryDuration] (in seconds).
   *
   * If an invalid refresh attempt is made, auto-refreshing is disabled.
   */
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

  /**
   * Returns the access token of the user
   */
  fun getAccessToken() = accessToken

  /**
   * Returns the expiry duration of the access token
   */
  fun getExpiryDuration() = expiryDuration

  /**
   * Get Spotify catalog information for a single album.
   *
   * This endpoint can be access anonymously.
   *
   * **Query Parameters:**
   * - **market** (Optional) - CountryCode of the market you wish to access the album from.
   *
   * @param albumId ID of the album to retrieve.
   * @see <a href="https://developer.spotify.com/documentation/web-api/reference/albums/get-album/">Endpoint Documentation</a>
   * @return [GetSingleAlbumQuery.Builder] to execute the query.
   */
  fun getSingleAlbum(albumId: String) = GetSingleAlbumQuery.Builder(accessToken, albumId)

  /**
   * Get Spotify catalog information about an album’s tracks
   *
   * @param albumId ID of the album to retrieve
   * @see <a href="https://developer.spotify.com/documentation/web-api/reference/albums/get-albums-tracks/">https://developer.spotify.com/documentation/web-api/reference/albums/get-albums-tracks/</a>
   */
  fun getAlbumTracks(albumId: String) = GetAlbumTracksQuery.Builder(accessToken, albumId)

  /**
   * Get Spotify catalog information for multiple albums identified by their Spotify IDs
   *
   * @see <a href="https://developer.spotify.com/documentation/web-api/reference/albums/get-several-albums/">https://developer.spotify.com/documentation/web-api/reference/albums/get-several-albums/</a>
   */
  fun getSeveralAlbums() = GetSeveralAlbumsQuery.Builder(accessToken)

  fun getSingleArtist(artistId: String) = GetSingleArtistQuery.Builder(accessToken, artistId)

  fun getArtistAlbums(artistId: String) = GetArtistAlbumsQuery.Builder(accessToken, artistId)

  fun getArtistTopTracks(artistId: String) = GetArtistTopTracksQuery.Builder(accessToken, artistId)

  fun getRelatedArtists(artistId: String) = GetRelatedArtistsQuery.Builder(accessToken, artistId)

  fun getSeveralArtists() = GetSeveralArtistsQuery.Builder(accessToken)

  fun getCategory(categoryId: String) = GetSingleCategoryQuery.Builder(accessToken, categoryId)

  fun getCategoryPlaylists(categoryId: String) = GetCategoryPlaylistsQuery.Builder(accessToken, categoryId)

  fun getCategoryList() = GetCategoryListQuery.Builder(accessToken)

  fun getFeaturedPlaylists() = GetFeaturedPlaylistsQuery.Builder(accessToken)

  fun getNewReleases() = GetNewReleasesQuery.Builder(accessToken)

  fun getRecommendationsFromSeed() = GetRecommendationFromSeedQuery.Builder(accessToken)

  fun getAvailableGenreSeeds() = GetAvailableGenreSeedsQuery.Builder(accessToken)

  fun isFollowingUserOrArtist(userType: UserType) = IsFollowingUserOrArtistQuery.Builder(accessToken, userType)

  fun areUsersFollowingPlaylist(playlistId: String) = AreUsersFollowingPlaylistQuery.Builder(accessToken, playlistId)

  fun followUserOrArtist() = FollowUserOrArtistQuery.Builder(accessToken)

  fun followPlaylist(playlistId: String) = FollowPlaylistQuery.Builder(accessToken, playlistId)

  fun getFollowedArtists() = GetFollowedArtistsQuery.Builder(accessToken)

  fun unfollowUserOrArtist(userType: UserType) = UnfollowUserOrArtistQuery.Builder(accessToken, userType)

  fun unfollowPlaylist(playlistId: String) = UnfollowPlaylistQuery.Builder(accessToken, playlistId)

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
   * Toggle shuffle on or off for user’s playback.
   *
   * @param state The state of the shuffle mode, **true** to shuffle the playback, **false** to stop shuffling the
   * playback
   * @see <a href="https://developer.spotify.com/documentation/web-api/reference/player/toggle-shuffle-for-users-playback/">https://developer.spotify.com/documentation/web-api/reference/player/toggle-shuffle-for-users-playback/</a>
   */
  fun toggleShuffle(state: Boolean) = ToggleShuffleQuery.Builder(accessToken, state)

  fun transferPlayback(targetDeviceId: String) = TransferPlaybackQuery.Builder(accessToken, targetDeviceId)

  /**
   * Adds a list of tracks to a given playlist
   *
   * @see <a href="https://developer.spotify.com/documentation/web-api/reference/playlists/add-tracks-to-playlist/">https://developer.spotify.com/documentation/web-api/reference/playlists/add-tracks-to-playlist/</a>
   */
  fun addTrackToPlaylist(playlistId: String) = AddTracksToPlaylistQuery.Builder(accessToken, playlistId)

  /**
   * Change a user owned playlist’s name and public/private state.
   *
   * @param playlistId The id of the playlist you wish to edit
   * @see <a href="https://developer.spotify.com/documentation/web-api/reference/playlists/change-playlist-details/">https://developer.spotify.com/documentation/web-api/reference/playlists/change-playlist-details/</a>
   */
  fun changePlaylistDetails(playlistId: String) = ChangePlaylistDetailsQuery.Builder(accessToken, playlistId)

  /**
   * Create an empty playlist for the current Spotify user
   *
   * @param playlistName The name of the new playlist
   * @see <a href="https://developer.spotify.com/documentation/web-api/reference/playlists/create-playlist/">https://developer.spotify.com/documentation/web-api/reference/playlists/create-playlist/</a>
   */
  fun createPlaylist(playlistName: String) = CreatePlaylistQuery.Builder(accessToken, playlistName)

  /**
   * Get a list of the playlists owned or followed by the current Spotify user
   *
   * @see <a href="https://developer.spotify.com/documentation/web-api/reference/playlists/get-a-list-of-current-users-playlists/">https://developer.spotify.com/documentation/web-api/reference/playlists/get-a-list-of-current-users-playlists/</a>
   */
  fun getCurrentUserPlaylists() = GetCurrentUserPlaylistsQuery.Builder(accessToken)

  /**
   * Get a list of the playlists owned or followed by a Spotify user
   *
   * @see <a href="https://developer.spotify.com/documentation/web-api/reference/playlists/get-list-users-playlists/">https://developer.spotify.com/documentation/web-api/reference/playlists/get-list-users-playlists/</a>
   */
  fun getUserPlaylists(userId: String) = GetUserPlaylistsQuery.Builder(accessToken, userId)

  fun getPlaylistCoverImage(playlistId: String) = GetPlaylistCoverImageQuery.Builder(accessToken, playlistId)

  fun getSinglePlaylist(playlistId: String) = GetSinglePlaylistQuery.Builder(accessToken, playlistId)

  fun getPlaylistTracks(playlistId: String) = GetPlaylistTracksQuery.Builder(accessToken, playlistId)

  fun removeTracksFromPlaylist(playlistId: String) = RemoveTracksFromPlaylistQuery.Builder(accessToken, playlistId)

  fun reorderPlaylistTracks(playlistId: String) = ReorderPlaylistTracksQuery.Builder(accessToken, playlistId)

  fun replacePlaylistTracks(playlistId: String) = ReplacePlaylistTracksQuery.Builder(accessToken, playlistId)

  fun uploadCustomPlaylistImage(playlistId: String) = UploadCustomPlaylistImageQuery.Builder(accessToken, playlistId)

  fun getTrackAudioAnalysis(trackId: String) = GetTrackAudioAnalysisQuery.Builder(accessToken, trackId)

  fun getSingleTrackAudioFeatures(trackId: String) = GetSingleTrackAudioFeaturesQuery.Builder(accessToken, trackId)

  fun getSeveralTracksAudioFeatures() = GetSeveralTracksAudioFeaturesQuery.Builder(accessToken)

  fun getSeveralTracks() = GetSeveralTracksQuery.Builder(accessToken)

  fun getSingleTrack(trackId: String) = GetSingleTrackQuery.Builder(accessToken, trackId)

  /**
   * Get detailed profile information about the current user
   *
   * @see <a href="https://developer.spotify.com/documentation/web-api/reference/users-profile/get-current-users-profile/>https://developer.spotify.com/documentation/web-api/reference/users-profile/get-current-users-profile/</a>
   */
  fun getCurrentUserProfile() = GetCurrentUserProfileQuery.Builder(accessToken)

  /**
   * Get public profile information about a Spotify user
   *
   * @param userId The user id of the target user
   * @see <a href="https://developer.spotify.com/documentation/web-api/reference/users-profile/get-users-profile/">https://developer.spotify.com/documentation/web-api/reference/users-profile/get-users-profile/</a>
   */
  fun getUserProfile(userId: String) = GetUserProfileQuery.Builder(accessToken, userId)
}