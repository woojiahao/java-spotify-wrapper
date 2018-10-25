package me.chill.queries.artist

import com.neovisionaries.i18n.CountryCode
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.Album
import me.chill.models.Paging


class ArtistAlbumQuery private constructor(
	private val id: String,
	private val accessToken: String,
	private val includeGroups: String,
	private val limit: Int,
	private val offset: Int,
	private val market: String?) : SpotifyArtistQuery() {

	enum class ArtistAlbumIncludeGroup(val queryValue: String) {
		Album("album"), Single("single"), AppearsOn("appears_on"), Compilation("compilation")
	}

	override fun execute(): Paging<Album> {
		val parameters = mapOf(
			"include_groups" to includeGroups,
			"limit" to limit,
			"offset" to offset,
			"market" to market
		)

		val response = query("$artistEndpoint$id/albums", accessToken, parameters)

		return gson.fromJson<Paging<Album>>(response.text, Paging::class.java)
	}

	class Builder(private val id: String, private val accessToken: String) {
		private var includeGroups = mutableListOf<ArtistAlbumIncludeGroup>()
		private var market: CountryCode? = null
		private var limit = 20
		private var offset = 0

		fun market(market: CountryCode): Builder {
			this.market = market
			return this
		}

		fun limit(limit: Int): Builder {
			if (limit < 1 || limit > 50) throw SpotifyQueryException("Limit cannot be less than 1 or more than 50")
			this.limit = limit
			return this
		}

		fun offset(offset: Int): Builder {
			if (offset < 0) throw SpotifyQueryException("Offset cannot be less than 0")
			this.offset = offset
			return this
		}

		fun addIncludeGroup(includeGroup: ArtistAlbumIncludeGroup): Builder {
			includeGroups.add(includeGroup)
			return this
		}

		fun setIncludeGroup(includeGroups: List<ArtistAlbumIncludeGroup>): Builder {
			if (includeGroups.isEmpty()) throw SpotifyQueryException("Include group cannot be empty")
			this.includeGroups.clear()
			this.includeGroups.addAll(includeGroups)
			return this
		}

		fun build(): ArtistAlbumQuery {
			if (includeGroups.isEmpty()) includeGroups.addAll(ArtistAlbumIncludeGroup.values())

			val includeGroupsString = includeGroups.asSequence().distinct().joinToString(",") { it.queryValue }
			return ArtistAlbumQuery(id, accessToken, includeGroupsString, limit, offset, market?.alpha2)
		}
	}
}