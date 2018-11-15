package me.chill.queries.browse

import com.neovisionaries.i18n.CountryCode
import me.chill.models.NewReleases
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLimit
import me.chill.utility.extensions.checkOffset
import me.chill.utility.extensions.read
import me.chill.utility.request.RequestMethod

class GetNewReleasesQuery private constructor(
  private val accessToken: String,
  private val limit: Int,
  private val offset: Int,
  private val country: String?) : AbstractQuery<NewReleases>(accessToken, RequestMethod.Get, "browse", "new-releases") {

  override fun execute(): NewReleases {
    val parameters = mapOf(
      "limit" to limit,
      "offset" to offset,
      "country" to country
    )

    return gson.read(checkedQuery(parameters).text)
  }

  class Builder(private val accessToken: String) {
    private var limit = 20
    private var offset = 0
    private var country: CountryCode? = null

    fun limit(limit: Int): Builder {
      this.limit = limit
      return this
    }

    fun offset(offset: Int): Builder {
      this.offset = offset
      return this
    }

    fun country(country: CountryCode): Builder {
      this.country = country
      return this
    }

    fun build(): GetNewReleasesQuery {
      limit.checkLimit()
      offset.checkOffset()

      return GetNewReleasesQuery(accessToken, limit, offset, country?.alpha2)
    }
  }
}