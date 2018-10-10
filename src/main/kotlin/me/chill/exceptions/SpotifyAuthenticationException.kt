package me.chill.exceptions

import me.chill.authentication.SpotifyAuthorizationFlow
import java.lang.Exception

/**
 * Creates a formatted exception message based on the errMap given
 */
class SpotifyAuthenticationException(errMap: Map<String, String>) : Exception(
	"\n\n${errMap.map { "\t${it.key}: ${it.value}" }.joinToString("\n")}\n") {
	var componentFail: SpotifyAuthorizationFlow.Component? = null

	constructor(cause: String) : this(mapOf("Cause" to cause))

	constructor(componentFail: SpotifyAuthorizationFlow.Component, errMap: Map<String, String>) : this(errMap) {
		this.componentFail = componentFail
	}
}