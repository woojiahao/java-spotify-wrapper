package me.chill.queries.album

import com.google.gson.JsonObject
import com.neovisionaries.i18n.CountryCode
import me.chill.models.Album
import me.chill.queries.checkEmpty
import me.chill.queries.checkListSizeLimit
import me.chill.queries.generateString

class GetSeveralAlbumsQuery private constructor(
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