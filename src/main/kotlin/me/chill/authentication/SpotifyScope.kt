package me.chill.authentication

/**
 * Authorization scopes for the user to access different endpoints.
 * @param scopeName Full name of the scope used for Spotify
 * @author Woo Jia Hao
 * @see <a href="https://developer.spotify.com/documentation/general/guides/scopes/">https://developer.spotify.com/documentation/general/guides/scopes/</a>
 */
enum class SpotifyScope(val scopeName: String) {
  // Playack
  Streaming("streaming"),
  AppRemoteControl("app-remote-control"),

  // Follow
  FollowModify("user-follow-modify"),
  FollowRead("user-follow-read"),

  // Playlist
  PrivatePlaylistRead("playlist-read-private"),
  PrivatePlaylistModify("playlist-modify-private"),
  CollaborativePlaylistRead("playlist-read-collaborative"),
  PublicPlaylistModify("playlist-modify-public"),

  // Spotify Connect
  PlaybackModify("user-modify-playback-state"),
  PlaybackRead("user-read-playback-state"),
  PlayingRead("user-read-currently-playing"),

  // Users
  AccountDetailsRead("user-read-private"),
  BirthDateRead("user-read-birthdate"),
  EmailRead("user-read-email"),

  // Library
  LibraryRead("user-library-read"),
  LibraryModify("user-library-modify"),

  // Listening History
  TopRead("user-top-read"),
  RecentlyPlayedRead("user-read-recently-played"),

  // Images
  ImageUpload("ugc-image-upload")
}