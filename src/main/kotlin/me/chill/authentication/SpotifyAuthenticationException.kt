package me.chill.authentication

import java.lang.Exception

/**
 * Creates a formatted exception message based on the errMap given
 */
class SpotifyAuthenticationException(errMap: Map<String, String>) : Exception(
	"\n\n${errMap.map { "\t${it.key}: ${it.value}" }.joinToString("\n")}\n") {
	var authorizationFlowComponentFail: SpotifyAuthorizationFlow.ParseComponent? = null
	var implicitGrantFlowComponentFail: SpotifyImplicitGrantFlow.ParseComponent? = null

	constructor(cause: String) : this(mapOf("Cause" to cause))

	constructor(parseComponentFail: SpotifyAuthorizationFlow.ParseComponent, errMap: Map<String, String>) : this(errMap) {
		this.authorizationFlowComponentFail = parseComponentFail
	}

	constructor(parseComponentFail: SpotifyImplicitGrantFlow.ParseComponent, errMap: Map<String, String>): this(errMap) {
		this.implicitGrantFlowComponentFail = parseComponentFail
	}
}