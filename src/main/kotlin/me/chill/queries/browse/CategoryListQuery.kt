package me.chill.queries.browse

import com.google.gson.JsonObject
import com.neovisionaries.i18n.CountryCode
import me.chill.models.Category
import me.chill.models.Paging
import java.util.*

class CategoryListQuery private constructor(
	private val accessToken: String,
	private val limit: Int,
	private val offset: Int,
	private val locale: String?,
	private val country: String?) : SpotifyBrowseQuery() {

	override fun execute(): Paging<Category> {
		val parameters = mapOf(
			"limit" to limit,
			"offset" to offset,
			"locale" to locale,
			"country" to country
		)

		val response = query("${browseEndpoint}categories", accessToken, parameters)

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