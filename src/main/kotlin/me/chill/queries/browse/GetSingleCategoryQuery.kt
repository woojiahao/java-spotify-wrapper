package me.chill.queries.browse

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Category
import me.chill.queries.AbstractQuery
import me.chill.utility.request.query
import java.util.*

class GetSingleCategoryQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val country: String?,
	private val locale: String?) : AbstractQuery("browse", "categories", id) {

	override fun execute(): Category {
		val parameters = mapOf(
			"country" to country,
			"locale" to locale
		)

		val response = query(queryEndpoint, accessToken, parameters)

		return gson.fromJson(response.text, Category::class.java)
	}

	class Builder(private val id: String, private val accessToken: String) {
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

		fun build() = GetSingleCategoryQuery(id, accessToken, country?.alpha2, locale)
	}
}