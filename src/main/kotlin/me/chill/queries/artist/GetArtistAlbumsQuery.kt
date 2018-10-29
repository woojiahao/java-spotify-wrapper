package me.chill.queries.artist

import com.neovisionaries.i18n.CountryCode
import me.chill.exceptions.SpotifyQueryException
import me.chill.models.Album
import me.chill.models.Paging
import me.chill.queries.checkLimit
import me.chill.queries.checkOffset


class GetArtistAlbumsQuery private constructor(
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

		val response = query(artistAlbumsEndpoint.format(id), accessToken, parameters)

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
			this.limit = limit
			return this
		}

		fun offset(offset: Int): Builder {
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

		fun build(): GetArtistAlbumsQuery {
			includeGroups.takeIf { it.isEmpty() }?.apply { addAll(ArtistAlbumIncludeGroup.values()) }
			val includeGroupsString = includeGroups.asSequence().distinct().joinToString(",") { it.queryValue }

			limit.checkLimit()
			offset.checkOffset()

			return GetArtistAlbumsQuery(id, accessToken, includeGroupsString, limit, offset, market?.alpha2)
		}
	}
}