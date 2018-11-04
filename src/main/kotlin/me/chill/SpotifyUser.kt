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

  /**
   * Get Spotify catalog information for a single album
   *
   * @param albumId ID of the album to retrieve
   * @see <a href="https://developer.spotify.com/documentation/web-api/reference/albums/get-album/">https://developer.spotify.com/documentation/web-api/reference/albums/get-album/</a>
   */
  fun getSingleAlbum(albumId: String) = GetSingleAlbumQuery.Builder(albumId, accessToken)

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