package me.chill.queries.album

import com.google.gson.JsonObject
import com.neovisionaries.i18n.CountryCode
import khttp.get
import me.chill.models.Album
import me.chill.queries.SpotifyQueryException
import me.chill.utility.responseCheck

class ManyAlbumQuery private constructor(
	private val accessToken: String,
	private val ids: List<String>,
	private val market: String?) : SpotifyAlbumQuery() {

	override fun execute(): List<Album?> {
		val parameters = mutableMapOf("ids" to ids.joinToString(","))

		market?.let { parameters["market"] = it }

		val response = get(primaryEndpoint, generateHeaders(accessToken), parameters)
		response.responseCheck()

		return gson
			.fromJson(response.text, JsonObject::class.java)
			.getAsJsonArray("albums")
			.map { gson.fromJson(it, Album::class.java) }
	}


	class Builder(private val accessToken: String) {
		private val ids = mutableListOf<String>()
		private var market: String? = null

		fun market(market: CountryCode): Builder {
			this.market = market.alpha2
			return this
		}

		fun addId(id: String): Builder {
			if (ids.size > 20) throw SpotifyQueryException("ID list cannot have more than 20 IDs")
			ids.add(id)
			return this
		}

		fun setIds(ids: List<String>): Builder {
			if (ids.size > 20) throw SpotifyQueryException("ID list cannot have more than 20 IDs")
			if (ids.isEmpty()) throw SpotifyQueryException("ID list must have at least 1 ID")
			this.ids.clear()
			this.ids.addAll(ids)
			return this
		}

		fun build(): ManyAlbumQuery {
			if (ids.isEmpty()) throw SpotifyQueryException("ID list must consist of at least 1 ID")
			return ManyAlbumQuery(accessToken, ids, market)
		}
	}
}