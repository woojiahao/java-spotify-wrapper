package me.chill.queries.playlist

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Playlist
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.RequestMethod

class GetSinglePlaylistQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val market: String?) : AbstractQuery<Playlist>(accessToken, RequestMethod.Get, "playlists", playlistId) {

  override fun execute(): Playlist =
    gson.read(checkedQuery(mapOf("market" to market)).text)

  class Builder(private val accessToken: String, private val playlistId: String) {
    private var market: CountryCode? = null

    fun market(market: CountryCode): Builder {
      this.market = market
      return this
    }

    fun build() = GetSinglePlaylistQuery(accessToken, playlistId, market?.alpha2)
  }
}