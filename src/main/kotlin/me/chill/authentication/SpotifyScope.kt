package me.chill.authentication

/**
 * Each constant represents a given scope that Spotify can request from the user to use
 */
enum class SpotifyScope(val scopeName: String) {
	// Streaming
	Streaming("streaming"),

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
	RecentlyPlayedRead("user-read-recently-played")
}