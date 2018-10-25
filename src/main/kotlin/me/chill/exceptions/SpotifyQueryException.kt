package me.chill.exceptions

class SpotifyQueryException(code: Int, message: String) : SpotifyException(
	mapOf(
		"Error Code" to code.toString(),
		"Error Message" to message
	)) {
	constructor(message: String) : this(-1, message)
}