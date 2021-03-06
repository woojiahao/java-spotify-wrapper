package me.chill.queries.library

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Paging
import me.chill.models.SavedAlbum
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLimit
import me.chill.utility.extensions.checkOffset
import me.chill.utility.extensions.read
import me.chill.utility.request.RequestMethod

class GetSavedAlbumsQuery private constructor(
  private val accessToken: String,
  private val limit: Int,
  private val offset: Int,
  private val market: String?) : AbstractQuery<Paging<SavedAlbum>>(accessToken, RequestMethod.Get, "me", "albums") {

  override fun execute(): Paging<SavedAlbum> {
    val parameters = mapOf(
      "limit" to limit,
      "offset" to offset,
      "market" to market
    )

    return gson.read(checkedQuery(parameters).text)
  }

  class Builder(private val accessToken: String) {
    private var limit = 20
    private var offset = 0
    private var market: CountryCode? = null

    fun limit(limit: Int): Builder {
      this.limit = limit
      return this
    }

    fun offset(offset: Int): Builder {
      this.offset = offset
      return this
    }

    fun market(market: CountryCode): Builder {
      this.market = market
      return this
    }

    fun build(): GetSavedAlbumsQuery {
      limit.checkLimit()
      offset.checkOffset()

      return GetSavedAlbumsQuery(accessToken, limit, offset, market?.alpha2)
    }
  }
}
