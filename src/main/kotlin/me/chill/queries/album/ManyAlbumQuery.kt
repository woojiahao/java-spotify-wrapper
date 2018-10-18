package me.chill.queries.album

import com.google.gson.JsonObject
import com.neovisionaries.i18n.CountryCode
import me.chill.models.Album
import me.chill.queries.SpotifyQueryException
import me.chill.utility.responseCheck

class ManyAlbumQuery private constructor(
	private val accessToken: String,
	private val ids: String,
	private val market: String?) : SpotifyAlbumQuery() {

	override fun execute(): List<Album?> {
		val parameters = mapOf(
			"ids" to ids,
			"market" to market
		)

		val response = query(albumEndpoint, accessToken, parameters)

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
			ids.add(id)
			return this
		}

		fun setIds(ids: List<String>): Builder {
			this.ids.clear()
			this.ids.addAll(ids)
			return this
		}

		fun build(): ManyAlbumQuery {
			if (ids.isEmpty()) throw SpotifyQueryException("ID list must consist of at least 1 ID")
			if (ids.size > 20) throw SpotifyQueryException("ID list cannot have more than 20 IDs")
			return ManyAlbumQuery(accessToken, ids.joinToString(","), market)
		}
	}
}