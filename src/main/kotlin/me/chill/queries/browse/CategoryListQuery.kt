package me.chill.queries.browse

import com.google.gson.JsonObject
import com.neovisionaries.i18n.CountryCode
import khttp.get
import me.chill.models.Category
import me.chill.models.Paging
import me.chill.utility.responseCheck
import java.util.*

class CategoryListQuery private constructor(
	private val accessToken: String,
	private val limit: Int,
	private val offset: Int,
	private val locale: String?,
	private val country: String?) : SpotifyBrowseQuery() {

	override fun execute(): Paging<Category> {
		val parameters = mutableMapOf(
			"limit" to limit.toString(),
			"offset" to offset.toString()
		)

		locale?.let { parameters["locale"] = it }
		country?.let { parameters["country"] = it }

		val response = get("${browseEndpoint}categories", generateHeaders(accessToken), parameters)

		response.responseCheck()

		return gson.fromJson<Paging<Category>>(gson.fromJson(response.text, JsonObject::class.java)["categories"], Paging::class.java)
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

		fun build() = CategoryListQuery(accessToken, limit, offset, locale, country?.alpha2)
	}
}