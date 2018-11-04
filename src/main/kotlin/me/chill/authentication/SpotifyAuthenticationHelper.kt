package me.chill.authentication

class SpotifyAuthenticationHelper private constructor(
  val clientId: String?,
  val clientSecret: String?,
  val redirectUrl: String?,
  val state: String?,
  val showDialog: Boolean,
  val scopes: List<SpotifyScope>) {

  class Builder {
    private var clientId: String? = null
    private var clientSecret: String? = null
    private var redirectUrl: String? = null
    private var state: String? = null

    private var showDialog = false
    private val scopes = mutableListOf<SpotifyScope>()

    fun clientId(clientId: String): Builder {
      this.clientId = clientId
      return this
    }

    fun clientSecret(clientSecret: String): Builder {
      this.clientSecret = clientSecret
      return this
    }

    fun showDialog(showDialog: Boolean): Builder {
      this.showDialog = showDialog
      return this
    }

    fun redirectUrl(url: String): Builder {
      this.redirectUrl = url
      return this
    }

    fun state(state: String): Builder {
      this.state = state
      return this
    }

    fun setScopes(scopes: List<SpotifyScope>): Builder {
      this.scopes.clear()
      this.scopes.addAll(scopes)
      return this
    }

    fun addScope(scope: SpotifyScope): Builder {
      scopes.add(scope)
      return this
    }

    fun build() = SpotifyAuthenticationHelper(clientId, clientSecret, redirectUrl, state, showDialog, scopes)
  }
}