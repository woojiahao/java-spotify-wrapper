package me.chill.exceptions

import me.chill.authentication.SpotifyAuthorizationFlow
import me.chill.authentication.SpotifyImplicitGrantFlow

class SpotifyAuthenticationException(errMap: Map<String, String>) : SpotifyException(errMap) {
  var authorizationFlowComponentFail: SpotifyAuthorizationFlow.ParseComponent? = null
  var implicitGrantFlowComponentFail: SpotifyImplicitGrantFlow.ParseComponent? = null

  constructor(cause: String) : this(mapOf("Cause" to cause))

  constructor(parseComponentFail: SpotifyAuthorizationFlow.ParseComponent, errMap: Map<String, String>) : this(errMap) {
    this.authorizationFlowComponentFail = parseComponentFail
  }

  constructor(parseComponentFail: SpotifyImplicitGrantFlow.ParseComponent, errMap: Map<String, String>) : this(errMap) {
    this.implicitGrantFlowComponentFail = parseComponentFail
  }
}