package me.chill.queries.browse

import com.google.gson.JsonObject
import com.neovisionaries.i18n.CountryCode
import me.chill.models.Category
import me.chill.models.Paging
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkLimit
import me.chill.utility.extensions.checkOffset
import me.chill.utility.extensions.read
import me.chill.utility.request.RequestMethod
import java.util.*

class GetCategoryListQuery private constructor(
  private val accessToken: String,
  private val limit: Int,
  private val offset: Int,
  private val locale: String?,
  private val country: String?) : AbstractQuery<Paging<Category>>(accessToken, RequestMethod.Get, "browse", "categories") {

  // TODO: Check this
  override fun execute(): Paging<Category> {
    val parameters = mapOf(
      "limit" to limit,
      "offset" to offset,
      "locale" to locale,
      "country" to country
    )

    return gson.read(gson.read<JsonObject>(checkedQuery(parameters).text)["categories"].toString())
  }

  class Builder(private val accessToken: String) {
    private var country: CountryCode? = null
    private var locale: String? = null
    private var limit = 20
    private var offset = 0

    fun country(country: CountryCode): Builder {
      this.country = country
      return this
    }

    fun limit(limit: Int): Builder {
      this.limit = limit
      return this
    }

    fun offset(offset: Int): Builder {
      this.offset = offset
      return this
    }

    fun locale(language: Locale, countryCode: CountryCode): Builder {
      this.locale = "${language.language}_${countryCode.alpha2}"
      return this
    }

    fun build(): GetCategoryListQuery {
      limit.checkLimit()
      offset.checkOffset()

      return GetCategoryListQuery(accessToken, limit, offset, locale, country?.alpha2)
    }
  }
}