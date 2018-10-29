package me.chill.queries.player

import me.chill.queries.Query

abstract class SpotifyPlayerQuery : Query() {
	protected val playerEndpoint = "${primaryEndpoint}v1/me/player/"
	protected val availableDevicesEndpoint = "${playerEndpoint}devices"
	protected val recentlyPlayedTracksEndpoint = "${playerEndpoint}recently-played"
	protected val currentlyPlayingEndpoint = "${playerEndpoint}currently-playing"
	protected val pauseEndpoint = "${playerEndpoint}pause"
	protected val seekEndpoint = "${playerEndpoint}seek"
	protected val repeatEndpoint = "${playerEndpoint}repeat"
	protected val volumeEndpoint = "${playerEndpoint}volume"
	protected val nextEndpoint = "${playerEndpoint}next"
	protected val previousEndpoint = "${playerEndpoint}previous"
	protected val playEndpoint = "${playerEndpoint}play"
	protected val shuffleEndpoint = "${playerEndpoint}shuffle"
}