package me.chill.queries.library

import khttp.delete
import me.chill.queries.checkListSizeLimit
import me.chill.queries.generateNullableString

class RemoveSavedAlbumsQuery private constructor(
	private val accessToken: String,
	private val ids: String?) : SpotifyLibraryQuery() {

	override fun execute(): Boolean {
		val parameters = mapOf("ids" to ids).generateParameters()

		val headers = mapOf(
			"Authorization" to "Bearer $accessToken",
			"Content-Type" to "application/json"
		)

		val response = delete(libraryEndpoint, headers, parameters)
		response.responseCheck()

		return response.statusCode == 200
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

		fun build(): RemoveSavedAlbumsQuery {
			albums.checkListSizeLimit("Albums", 50)

			return RemoveSavedAlbumsQuery(accessToken, albums.generateNullableString())
		}
	}
}
