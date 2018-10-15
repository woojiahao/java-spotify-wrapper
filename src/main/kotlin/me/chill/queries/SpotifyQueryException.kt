package me.chill.queries

import me.chill.utility.createErrorMessage
import java.lang.Exception

class SpotifyQueryException(code: Int, message: String) : Exception(
	createErrorMessage(
		mapOf(
			"Error Code" to code.toString(),
			"Error Message" to message
		)
	)
) {
	constructor(message: String) : this(-1, message)
}