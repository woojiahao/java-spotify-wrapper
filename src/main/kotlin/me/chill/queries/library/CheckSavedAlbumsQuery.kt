package me.chill.queries.library

import me.chill.queries.checkEmpty
import me.chill.queries.checkListSizeLimit
import me.chill.queries.createCheckMap
import me.chill.queries.generateString

class CheckSavedAlbumsQuery private constructor(
	private val accessToken: String,
	private val ids: String) : SpotifyLibraryQuery() {

	override fun execute(): Map<String, Boolean> {
		val parameters = mapOf("ids" to ids)

		val response = query("${libraryEndpoint}contains", accessToken, parameters)

		return response.createCheckMap(ids, gson)
	}

	class Builder(private val accessToken: String) {
		private val albums = mutableListOf<String>()

		fun addAlbum(album: String): Builder {
			albums.add(album)
			return this
		}

		fun setAlbums(albums: List<String>): Builder {
			this.albums.clear()
			this.albums.addAll(albums)
			return this
		}

		fun build(): CheckSavedAlbumsQuery {
			albums.checkEmpty("Albums")
			albums.checkListSizeLimit("Albums", 50)

			return CheckSavedAlbumsQuery(accessToken, albums.generateString())
		}
	}
}