package me.chill.queries.browse

import com.neovisionaries.i18n.CountryCode
import khttp.get
import me.chill.models.Category
import me.chill.utility.responseCheck
import java.util.*

class SingleCategoryQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val country: String?,
	private val locale: String?) : SpotifyBrowseQuery() {

	override fun execute(): Category {
		val parameters = mutableMapOf<String, String>()
		country?.let { parameters["country"] = it }
		locale?.let { parameters["locale"] = it }

		val response = get("${browseEndpoint}categories/$id", generateHeaders(accessToken), parameters)

		response.responseCheck()

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

		fun build() = SingleCategoryQuery(id, accessToken, country?.alpha2, locale)
	}
}