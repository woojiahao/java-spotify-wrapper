package me.chill.queries.album

import com.neovisionaries.i18n.CountryCode
import me.chill.models.Album
import me.chill.queries.AbstractQuery
import me.chill.utility.extensions.checkEmpty
import me.chill.utility.extensions.checkListSizeLimit
import me.chill.utility.extensions.generateString
import me.chill.utility.request.query
import me.chill.utility.request.readFromJsonArray

class GetSeveralAlbumsQuery private constructor(
	private val accessToken: String,
	private val ids: String,
	private val market: String?) : AbstractQuery("albums") {

	override fun execute(): List<Album?> {
		val parameters = mapOf(
			"ids" to ids,
			"market" to market
		)

		val response = query(queryEndpoint, accessToken, parameters)

		return gson.readFromJsonArray("albums", response)
	}


	class Builder(private val accessToken: String) {
		private val albums = mutableListOf<String>()
		private var market: String? = null

		fun market(market: CountryCode): Builder {
			this.market = market.alpha2
			return this
		}

		fun addId(album: String): Builder {
			albums.add(album)
			return this
		}

		fun setIds(albums: List<String>): Builder {
			this.albums.clear()
			this.albums.addAll(albums)
			return this
		}

		fun build(): GetSeveralAlbumsQuery {
			albums.checkEmpty("Albums")
			albums.checkListSizeLimit("Albums")

			return GetSeveralAlbumsQuery(accessToken, albums.generateString(), market)
		}
	}
}