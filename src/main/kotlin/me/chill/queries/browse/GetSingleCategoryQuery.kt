package me.chill.queries.browse

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Category
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.read
import me.chill.utility.request.RequestMethod
import java.util.*

class GetSingleCategoryQuery private constructor(
  private val accessToken: String,
  private val categoryId: String,
  private val country: String?,
  private val locale: String?) : AbstractQuery<Category>(accessToken, RequestMethod.Get, "browse", "categories", categoryId) {

  override fun execute(): Category {
    val parameters = mapOf(
      "country" to country,
      "locale" to locale
    )

    return gson.read(checkedQuery(parameters).text)
  }

  class Builder(private val accessToken: String, private val categoryId: String) {
    private var country: CountryCode? = null
    private var locale: String? = null

    fun country(country: CountryCode): Builder {
      this.country = country
      return this
    }

    fun locale(languageCode: Locale, countryCode: CountryCode): Builder {
      locale = "${languageCode.language}_${countryCode.alpha2}"
      return this
    }

    fun build() = GetSingleCategoryQuery(accessToken, categoryId, country?.alpha2, locale)
  }
}