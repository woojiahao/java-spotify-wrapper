package me.chill.authentication

import me.chill.utility.createErrorMessage
import java.lang.Exception

class SpotifyAuthenticationException(errMap: Map<String, String>) : Exception(createErrorMessage(errMap)) {
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