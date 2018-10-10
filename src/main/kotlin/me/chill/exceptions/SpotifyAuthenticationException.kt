package me.chill.exceptions

import java.lang.Exception

/**
 * Creates a formatted exception message based on the errMap given
 */
class SpotifyAuthenticationException(errMap: Map<String, String>) : Exception(
	"\n\n${errMap.map { "\t${it.key}: ${it.value}" }.joinToString("\n")}\n") {
	constructor(cause: String) : this(mapOf("Cause" to cause))
}