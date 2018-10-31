package me.chill.exceptions

class SpotifyQueryException(val code: Int, val description: String) : SpotifyException(
	mapOf(
		"Error Code" to code.toString(),
		"Error Message" to description
	)) {
	constructor(message: String) : this(-1, message)
}